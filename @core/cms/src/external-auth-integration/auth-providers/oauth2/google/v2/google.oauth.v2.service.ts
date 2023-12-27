import { Injectable, Logger, Res } from "@nestjs/common";
import { IOAuth } from "../../interface/ioauth.interface";
import { GoogleOAuthV2Config } from "./google.v2.config";
import { EncryptionDecryptionService } from "src/encryption-decryption/encryption-decryption.service";

@Injectable()
export class GoogleOAuthV2Service implements IOAuth {
  constructor(
    private readonly config: GoogleOAuthV2Config,
    private readonly encryptionDecryptionService: EncryptionDecryptionService
  ) {}

  async authenticate(): Promise<string> {
    // Build auth redirect url
    const clientId = this.config.clientId;
    const redirectUri = this.config.redirectUri;
    const scope = encodeURIComponent(this.config.scope.join(" "));

    const encodedState = await this.buildState();
    const authUrl = `${this.config.authUrl}?client_id=${clientId}&redirect_uri=${redirectUri}&scope=${scope}&response_type=code&state=${encodedState}`;
    Logger.log(
      `Redirecting to Google OAuth URL: ${authUrl}`,
      "GoogleOAuthV2Service"
    );
    return authUrl;
  }

  private async buildState(): Promise<string> {
    // TODO: Use Auth to pass real user state id
    const userId = "aaaa";
    const providerInfo = {
      provider: "google",
      version: "v2",
    };
    const state = JSON.stringify({
      userId,
      providerInfo,
    });

    const encryptedState =
      await this.encryptionDecryptionService.encryptData(state);

    const base64UrlEncodedState = Buffer.from(encryptedState)
      .toString("base64")
      .replace(/\+/g, "-")
      .replace(/\//g, "_")
      .replace(/=+$/, "");

    return base64UrlEncodedState;
  }

  async handleCallback(query: any, @Res() res: any) {
    const code = query.code;

    Logger.log(`Received code: ${code}`, "GoogleOAuthV2Service");
    res.status(200).send(`Received code: ${code}`);
  }
}
