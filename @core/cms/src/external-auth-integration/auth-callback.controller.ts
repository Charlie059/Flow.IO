import { Controller, Get, Logger, Query, Res } from "@nestjs/common";
import { AuthProviderFactory } from "./auth-provider.factory";
import { EncryptionDecryptionService } from "src/encryption-decryption/encryption-decryption.service";

@Controller("oauth")
export class AuthCallbackController {
  constructor(
    private providerFactory: AuthProviderFactory,
    private encryptionDecryptionService: EncryptionDecryptionService
  ) {}

  @Get("callback")
  async handleCallback(@Query() query: any, @Res() res: Response) {
    const encodedState = query.state;

    // Decode Base 64 URL to standardBase64
    const standardBase64 = encodedState
      .replace(/-/g, "+")
      .replace(/_/g, "/")
      .concat("=".repeat((4 - (encodedState.length % 4)) % 4));

    const encryptedState = Buffer.from(standardBase64, "base64").toString();

    // Decrypt State
    const decryptedState =
      await this.encryptionDecryptionService.decryptData(encryptedState);

    const state = JSON.parse(decryptedState);
    const key = `oauth-${state.providerInfo.provider}-${state.providerInfo.version}`;
    const providerService = this.providerFactory.getProvider(key);
    await providerService.handleCallback(query, res);
  }
}
