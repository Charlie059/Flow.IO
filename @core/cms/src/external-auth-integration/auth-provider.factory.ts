import { Injectable } from "@nestjs/common";
import { GoogleV2Service } from "./auth-providers/oauth2/google/v2/google.v2.service";
import { IOAuth } from "./auth-providers/oauth2/interface/ioauth.interface";

@Injectable()
export class AuthProviderFactory {
  private readonly providerMap = new Map<string, IOAuth>();

  constructor(
    private googleV2OAuthService: GoogleV2Service
  ) {
    this.registerProvider("oauth-google-v2", this.googleV2OAuthService);
  }

  private registerProvider(key: string, provider: IOAuth) {
    this.providerMap.set(key, provider);
  }

  public getProvider(key: string): IOAuth {
    const provider = this.providerMap.get(key);
    if (!provider) {
      throw new Error(`Provider not found: ${key}`);
    }
    return provider;
  }
}
