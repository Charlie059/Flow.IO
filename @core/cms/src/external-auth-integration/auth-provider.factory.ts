import { Injectable } from "@nestjs/common";
import { IOAuth } from "./auth-providers/oauth2/interface/ioauth.interface";
import { GoogleV2OAuth2Service } from "./auth-providers/oauth2/google/v2/google.v2.service";
import { GithubV1OAuth2Service } from "./auth-providers/oauth2/github/v1/github.v1.service";
import { AirtableV1OAuth2Service } from "./auth-providers/oauth2/airtable/v1/airtable.v1.service";

@Injectable()
export class AuthProviderFactory {
  private readonly providerMap = new Map<string, IOAuth>();

  constructor(
    private googleV2OAuth2Service: GoogleV2OAuth2Service,
    private githubV1OAuth2Service: GithubV1OAuth2Service,
    private airtableV1OAuth2Service: AirtableV1OAuth2Service
  ) {
    this.registerProvider("oauth-google-v2", this.googleV2OAuth2Service);
    this.registerProvider("oauth-github-v1", this.githubV1OAuth2Service);
    this.registerProvider("oauth-airtable-v1", this.airtableV1OAuth2Service);
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
