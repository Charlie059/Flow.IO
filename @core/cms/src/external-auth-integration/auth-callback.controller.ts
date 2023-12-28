import {
  Controller,
  Get,
  Logger,
  Query,
  Res,
  HttpException,
  HttpStatus,
} from "@nestjs/common";
import { AuthProviderFactory } from "./auth-provider.factory";
import { EncryptionDecryptionService } from "src/encryption-decryption/encryption-decryption.service";
import { decodeBase64UrlToString } from "./utils";

@Controller("oauth")
export class AuthCallbackController {
  constructor(
    private providerFactory: AuthProviderFactory,
    private encryptionDecryptionService: EncryptionDecryptionService
  ) {}

  /**
   * Define the OAuth callback route.
   * @throws {HttpException} - If the state parameter is missing or invalid.
   * @param query
   * @param res
   */
  @Get("callback")
  async handleCallback(@Query() query: any, @Res() res: Response) {
    if (!query.state) {
      Logger.error("No state received", "AuthCallbackController");
      throw new HttpException(
        "State parameter is missing",
        HttpStatus.BAD_REQUEST
      );
    }

    try {
      const encryptedState = decodeBase64UrlToString(query.state);
      const decryptedState =
        await this.encryptionDecryptionService.decryptData(encryptedState);
      const state = JSON.parse(decryptedState);

      if (
        !state.providerInfo ||
        !state.providerInfo.provider ||
        !state.providerInfo.version
      ) {
        throw new Error("Invalid state format");
      }

      // TODO: Verify user JWT

      const key = `oauth-${state.providerInfo.provider}-${state.providerInfo.version}`;
      const providerService = this.providerFactory.getProvider(key);
      await providerService.handleCallback(query, res);
    } catch (error) {
      Logger.error(error.message, "AuthCallbackController");
      throw new HttpException(
        "Error processing the OAuth callback",
        HttpStatus.INTERNAL_SERVER_ERROR
      );
    }
  }
}
