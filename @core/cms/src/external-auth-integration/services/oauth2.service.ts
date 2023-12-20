import { Injectable, Logger } from "@nestjs/common";
import { ConfigService } from "@nestjs/config";
import { IAuth } from "./interface/auth.interface";

@Injectable()
export class OAuthService implements IAuth {
  constructor(private configService: ConfigService) {}
  authenticate(provider: string): string {
    Logger.log(`Authenticating with ${provider}`, "OAuthService");
    const providerUpperCase = provider.toUpperCase();
    const clientId = this.configService.get(`${providerUpperCase}_CLIENT_ID`);
    const redirectUri = this.configService.get(
      `${providerUpperCase}_REDIRECT_URI`
    );
    const authUrl = this.configService.get(`${providerUpperCase}_AUTH_URL`);
    const scope = "email profile";

    const encodedRedirectUri = encodeURIComponent(redirectUri);
    const encodedScope = encodeURIComponent(scope);

    const url = `${authUrl}?client_id=${clientId}&redirect_uri=${encodedRedirectUri}&scope=${encodedScope}&response_type=code`;

    Logger.log(`Redirecting to ${url}`, "OAuthService");
    return url;
  }
  validate(...args: any[]): Promise<any> {
    throw new Error("Method not implemented.");
  }
}
