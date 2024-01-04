import { Injectable } from "@nestjs/common";
import { IOAuth } from "./auth-providers/oauth2/interface/ioauth.interface";
import { GoogleOAuthV2Service } from "./auth-providers/oauth2/google/v2/google.oauth.v2.service";
import { GithubOAuthV2Service } from "./auth-providers/oauth2/github/v2/github.oauth.v2.service";

@Injectable()
export class AuthProviderFactory {
  private readonly providerMap = new Map<string, IOAuth>();

  constructor(
    private googleOAuthV2Service: GoogleOAuthV2Service,
    private githubOAuthV2Service: GithubOAuthV2Service
  ) {
    this.registerProvider("oauth-google-v2", this.googleOAuthV2Service);
    this.registerProvider("oauth-github-v2", this.githubOAuthV2Service);
  }

  private registerProvider(key: string, provider: IOAuth) {
    this.providerMap.set(key, provider);
  }

  public getProvider(key: string) {
    const provider = this.providerMap.get(key);
    if (!provider) {
      throw new Error(`Provider not found: ${key}`);
    }
    return provider;
  }
}
