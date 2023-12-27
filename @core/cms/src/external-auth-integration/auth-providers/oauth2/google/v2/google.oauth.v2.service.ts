import {
  Injectable,
  Inject,
  Logger,
  Res,
  HttpException,
  HttpStatus,
} from "@nestjs/common";
import { IOAuth } from "../../interface/ioauth.interface";
import { EncryptionDecryptionService } from "src/encryption-decryption/encryption-decryption.service";
import { HttpService } from "@nestjs/axios";
import {
  OAuth2StateProcessor,
  createOAuth2Url,
  exchangeCodeForToken,
} from "src/external-auth-integration/utils";
import { IOAuth2Config, IOAuth2State } from "../../@types";

/**
 * Service to handle Google OAuth V2 authentication.
 */
@Injectable()
export class GoogleOAuthV2Service implements IOAuth {
  constructor(
    @Inject("GoogleOAuthV2Config") private readonly config: IOAuth2Config,
    private readonly encryptionDecryptionService: EncryptionDecryptionService,
    private readonly httpService: HttpService
  ) {}

  /**
   * Builds and returns the authentication URL for Google OAuth.
   * @returns {Promise<string>} The Google OAuth URL.
   */
  async authenticate(): Promise<string> {
    const {
      credentials: {
        client: { id: clientId },
      },
      scope,
      callbackUri,
    } = this.config;
    const redirectUri = callbackUri;
    const joinedScope = scope.join(" ");

    const encodedState = await this.buildState();
    const url = createOAuth2Url(this.config, {
      client_id: clientId,
      redirect_uri: redirectUri,
      scope: joinedScope,
      state: encodedState,
    });

    Logger.log(`Redirecting to Google OAuth URL: ${url}`);
    return url;
  }

  /**
   * Builds the state parameter for OAuth authentication.
   * @private
   * @returns {Promise<string>} The base64 URL-encoded state.
   */
  private async buildState(): Promise<string> {
    const oAuth2State: IOAuth2State = {
      userId: "aaaa", // Replace with real user ID
      providerInfo: {
        provider: "google",
        version: "v2",
      },
    };

    return new OAuth2StateProcessor(
      oAuth2State,
      this.encryptionDecryptionService
    )
      .stringify()
      .then((p) => p.encrypt())
      .then((p) => p.toBase64())
      .then((p) => p.getResult());
  }

  /**
   * Handles the OAuth callback and exchanges the code for a token.
   * @param {any} query - The query parameters from the callback request.
   * @param {any} res - The response object.
   */
  async handleCallback(query: any, @Res() res: any) {
    try {
      if (!query.code) {
        throw new HttpException("No code received", HttpStatus.BAD_REQUEST);
      }

      const tokenResponse = await exchangeCodeForToken(
        this.httpService,
        this.config,
        query.code
      );

      res.status(HttpStatus.OK).json(tokenResponse);
    } catch (error) {
      Logger.error("Error exchanging code for token", error);
      throw new HttpException(
        "Error exchanging code for token",
        HttpStatus.INTERNAL_SERVER_ERROR
      );
    }
  }
}
