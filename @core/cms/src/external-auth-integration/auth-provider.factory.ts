import { Injectable } from "@nestjs/common";
import { GoogleV2Service } from "./auth-providers/oauth2/google/v2/google.v2.service";
import { IOAuth } from "./auth-providers/oauth2/interface/ioauth.interface";
import { AirtableV1Service } from "./auth-providers/oauth2/airtable/v1/airtable.v1.service";

@Injectable()
export class AuthProviderFactory {
  private readonly providerMap = new Map<string, IOAuth>();

  constructor(
    private googleV2OAuthService: GoogleV2Service,
    private airtableV1OAuthService: AirtableV1Service
  ) {
    this.registerProvider("oauth-google-v2", this.googleV2OAuthService);
    this.registerProvider("oauth-airtable-v1", this.airtableV1OAuthService);
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
