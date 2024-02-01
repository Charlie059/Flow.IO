import { HttpException, HttpServer, HttpStatus, Inject, Injectable, Logger, Res } from "@nestjs/common";
import { IOAuth } from "../interface/ioauth.interface";
import { IOAuth2Config, IOAuth2State, TokenVerificationResponse } from "../@types";
import { EncryptionDecryptionService } from "~/encryption-decryption/encryption-decryption.service";
import { OAuth2StateProcessor, createOAuth2Url, exchangeCodeForToken, refreshToken, verifyToken } from "~/external-auth-integration/utils";
import { HttpService } from "@nestjs/axios";

/**
 * Service to handle Stripe OAuth2 authentication
 */
@Injectable()
export class StripeOauth2Service implements IOAuth {
  constructor(
    @Inject("StripeOAuth2Config")
    private readonly config: IOAuth2Config,
    private readonly encryptionDecryptionService: EncryptionDecryptionService,
    private readonly httpService: HttpService,
  ) {}

  /**
   * Builds and returns the authentication URL for Google OAuth.
   * @returns {Promise<string>} The Slack OAuth URL.
   */
  async authenticate(): Promise<string> {
    const encodedState = await this.buildState();
    const baseUrl = this.config.provider.authorizeUrl;
    const params = new URLSearchParams({
      response_type: "code",
      client_id: this.config.credentials.id,
      scope: "read_write",
    });

    const url = `${baseUrl}?${params.toString()}`;
    Logger.log(`Redirecting to Stripe OAuth URL: ${url}`);
    return url;
  }

  /**
   * Handles the OAuth callback and exchanges the code for a token.
   *  https://connect.stripe.com/oauth/authorize?response_type=code&client_id=ca_PRZ1oUmSwhaIzemYG9ozgihOJKejww62&scope=read_write

   * @param {any} query - The query parameters from the callback request.
   * @param {any} res - The response object.
   */
  async handleCallback(query: any, @Res() res: any) {
    try {
      if (!query || !query.code) {
        Logger.error("No code received", "StripeOAuth2Service");
        throw new HttpException("No code received", HttpStatus.BAD_REQUEST);
      }

      const tokenResponse = await exchangeCodeForToken(this.httpService, this.config, query.code);

      res.status(HttpStatus.OK).json(tokenResponse);
    } catch (error) {
      Logger.error("Error exchanging code for token", error);
      throw new HttpException("Error exchanging code for token", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * Refreshes the access token using the refresh token.
   * @param _refreshToken - The refresh token.
   */
  refreshToken(_refreshToken: string): Promise<any> {
    return refreshToken(this.httpService, this.config, _refreshToken);
  }

  /**
   * Verifies the access token.
   * @param _accessToken
   * @returns {Promise<any>} The response from the token verification endpoint.
   */
  async verifyToken(_accessToken: string): Promise<TokenVerificationResponse> {
    try {
      const verificationResponse = await verifyToken(this.httpService, this.config, _accessToken);
      return {
        isValid: true,
        expiresIn: verificationResponse.expires_in,
        scopes: verificationResponse.scope.split(" "),
      } as TokenVerificationResponse;
    } catch (error) {
      Logger.error("Token verification failed", error);
      return { isValid: false };
    }
  }

  /**
   * Builds the state parameter for OAuth authentication.
   * @private
   * @returns {Promise<string>} The base64 URL-encoded state.
   */
  private async buildState(): Promise<string> {
    const oAuth2State: IOAuth2State = {
      userId: "acct_1OcftAF5sEUxsrpA", // TODO: Replace with real user ID
      providerInfo: {
        provider: "stripe",
        version: "v2",
      },
    };

    return new OAuth2StateProcessor(oAuth2State, this.encryptionDecryptionService)
      .stringify()
      .then((p) => p.encrypt())
      .then((p) => p.toBase64())
      .then((p) => p.getResult());
  }
}
