import { Controller, Get, Logger, Query, Res } from "@nestjs/common";
import { AuthProviderFactory } from "./auth-provider.factory";
import { EncryptionDecryptionService } from "src/encryption-decryption/encryption-decryption.service";
import { decodeBase64UrlToString } from "./utils";

@Controller("oauth")
export class AuthCallbackController {
  constructor(
    private providerFactory: AuthProviderFactory,
    private encryptionDecryptionService: EncryptionDecryptionService
  ) {}

  @Get("callback")
  async handleCallback(@Query() query: any, @Res() res: Response) {
    const encodedState = query.state;

    const encryptedState = decodeBase64UrlToString(encodedState);

    // Decrypt State
    const decryptedState =
      await this.encryptionDecryptionService.decryptData(encryptedState);

    Logger.log(`Decrypted State: ${decryptedState}`, "AuthCallbackController");

    const state = JSON.parse(decryptedState);
    const key = `oauth-${state.providerInfo.provider}-${state.providerInfo.version}`;

    // TODO: Error handling
    const providerService = this.providerFactory.getProvider(key);

    await providerService.handleCallback(query, res);
  }
}
