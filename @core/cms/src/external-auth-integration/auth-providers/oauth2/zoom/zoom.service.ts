import { Injectable, Inject, Logger, Res, HttpException, HttpStatus } from "@nestjs/common";
import { IOAuth } from "~/external-auth-integration/auth-providers/oauth2/interface/ioauth.interface";
import { EncryptionDecryptionService } from "~/encryption-decryption/encryption-decryption.service";
import { HttpService } from "@nestjs/axios";
import {
  OAuth2StateProcessor,
  exchangeCodeForToken,
  verifyToken,
  refreshToken,
  createOAuth2Url,
} from "~/external-auth-integration/utils";
import { IOAuth2Config, IOAuth2State, TokenVerificationResponse } from "~/external-auth-integration/auth-providers/oauth2/@types";

/**
 * Service to handle Zoom V1 OAuth2 authentication.
 */
@Injectable()
export class ZoomOAuth2Service implements IOAuth {
  constructor(
    @Inject("ZoomOAuth2Config") private readonly config: IOAuth2Config,
    private readonly encryptionDecryptionService: EncryptionDecryptionService,
    private readonly httpService: HttpService,
  ) {}

  /**
   * Builds and returns the authentication URL for Github OAuth.
   * @returns {Promise<string>} The Zoom OAuth URL.
   */
  async authenticate(): Promise<string> {
    const encodedState = await this.buildState();
    const url = createOAuth2Url(this.config, { state: encodedState }, "authorize");

    Logger.log(`Redirecting to Zoom OAuth URL: ${url}`);
    return url;
  }

  /**
   * Handles the OAuth callback and exchanges the code for a token.
   * @param {any} query - The query parameters from the callback request.
   * @param {any} res - The response object.
   */
  async handleCallback(query: any, @Res() res: any) {
    try {
      if (!query || !query.code) {
        Logger.error("No code received", "ZoomOAuth2Service");
        throw new HttpException("No code received", HttpStatus.BAD_REQUEST);
      }

      const tokenResponse = await exchangeCodeForToken(this.httpService, this.config, query.code);
      Logger.log("Token response", tokenResponse);
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
      userId: "aaaa", // TODO: Replace with real user ID
      providerInfo: {
        provider: "zoom",
        version: "v1",
      },
    };

    return new OAuth2StateProcessor(oAuth2State, this.encryptionDecryptionService)
      .stringify()
      .then((p) => p.encrypt())
      .then((p) => p.toBase64())
      .then((p) => p.getResult());
  }
}
