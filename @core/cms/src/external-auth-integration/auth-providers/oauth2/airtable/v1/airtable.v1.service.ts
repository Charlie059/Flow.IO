import { Injectable, Inject, Logger, Res, HttpException, HttpStatus } from "@nestjs/common";
import { HttpService } from "@nestjs/axios";
import { CACHE_MANAGER } from "@nestjs/cache-manager";
import { Cache } from "cache-manager";
import { randomBytes } from "crypto";
import { EncryptionDecryptionService } from "~/encryption-decryption/encryption-decryption.service";
import { IOAuth } from "~/external-auth-integration/auth-providers/oauth2/interface/ioauth.interface";
import {
  OAuth2StateProcessor,
  exchangeCodeForToken,
  verifyToken,
  refreshToken,
  createOAuth2Url,
  toSha256Base64UrlSafe,
} from "~/external-auth-integration/utils";
import { IOAuth2Config, IOAuth2State, TokenVerificationResponse } from "~/external-auth-integration/auth-providers/oauth2/@types";

/**
 * Service to handle Airtable V1 OAuth2 authentication.
 */
@Injectable()
export class AirtableV1OAuth2Service implements IOAuth {
  constructor(
    @Inject("AirtableV1OAuth2Config") private readonly config: IOAuth2Config,
    private readonly encryptionDecryptionService: EncryptionDecryptionService,
    private readonly httpService: HttpService,
    @Inject(CACHE_MANAGER) private cacheManager: Cache, // TODO: refactor
  ) {}

  /**
   * Builds and returns the authentication URL for Airtable OAuth.
   * @returns {Promise<string>} The Airtable OAuth URL.
   */
  async authenticate(): Promise<string> {
    const encodedState = await this.buildState();
    const codeVerifier = randomBytes(96).toString("base64url");
    // TODO: refactor
    await this.cacheManager.set("userId-oauth2-airtable-v1", codeVerifier, 300_000); // code verifier ttl: 5 mins

    Logger.debug(`Generated code verifier: ${codeVerifier}`, "AirtableV1OAuth2Service");
    const codeChallenge = toSha256Base64UrlSafe(codeVerifier);
    const url = createOAuth2Url(
      this.config,
      {
        state: encodedState,
        code_challenge_method: "S256",
        code_challenge: codeChallenge,
      },
      "authorize",
    );

    Logger.log(`Redirecting to Airtable OAuth URL: ${url}`);
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
        Logger.error("No code received", "AirtableV1OAuth2Service");
        throw new HttpException("No code received", HttpStatus.BAD_REQUEST);
      }

      const encodedCredentials = Buffer.from(`${this.config.credentials.id}:${this.config.credentials.secret}`).toString("base64")
      const tokenResponse = await exchangeCodeForToken(this.httpService, this.config, query.code, {
        params: {
          code_verifier: await this.cacheManager.get("userId-oauth2-airtable-v1"), // TODO: refactor
        },
        headers: {
          Authorization: `Basic ${encodedCredentials}`,
          "Content-Type": "application/x-www-form-urlencoded",
        }
      });

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
  async refreshToken(_refreshToken: string): Promise<any> {
    try {
      const encodedCredentials = Buffer.from(`${this.config.credentials.id}:${this.config.credentials.secret}`).toString("base64")
      const response = await refreshToken(this.httpService, this.config, _refreshToken, {
        headers: {
          Authorization: `Basic ${encodedCredentials}`,
          "Content-Type": "application/x-www-form-urlencoded",
        }
      });
      Logger.log("Refresh token response", response);
      return response;
    } catch (error) {
      Logger.error("Error refreshing token", error);
      throw new HttpException("Error refreshing token", HttpStatus.INTERNAL_SERVER_ERROR);
    }
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
        provider: "airtable",
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
