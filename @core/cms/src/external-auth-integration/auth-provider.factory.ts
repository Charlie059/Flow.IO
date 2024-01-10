import { Injectable } from "@nestjs/common";
import { AuthString, AuthScheme } from "./@types";
import { GoogleV2OAuth2Service } from "./auth-providers/oauth2/google/v2/google.v2.service";
import { GithubV1OAuth2Service } from "./auth-providers/oauth2/github/v1/github.v1.service";
import { OAuthString } from "./auth-providers/oauth2/@types";
import { IOAuth } from "./auth-providers/oauth2/interface/ioauth.interface";
import { BasicAuthString } from "./auth-providers/basic-auth/@types";
import { IBasicAuth } from "./auth-providers/basic-auth/interface/basic-auth.interface";

@Injectable()
export class AuthProviderFactory {
  private readonly providerMap = new Map<AuthString, AuthScheme>();

  constructor(
    private googleV2OAuth2Service: GoogleV2OAuth2Service,
    private githubV1OAuth2Service: GithubV1OAuth2Service,
  ) {
    this.registerProvider("oauth-google-v2", this.googleV2OAuth2Service);
    this.registerProvider("oauth-github-v1", this.githubV1OAuth2Service);
  }

  private registerProvider(key: OAuthString, provider: IOAuth): void;
  private registerProvider(key: BasicAuthString, provider: IBasicAuth): void;
  private registerProvider(key: AuthString, provider: AuthScheme): void {
    this.providerMap.set(key, provider);
  }

  public getProvider(key: OAuthString): IOAuth;
  public getProvider(key: BasicAuthString): IBasicAuth;
  public getProvider(key: AuthString): AuthScheme;
  public getProvider(key: AuthString): AuthScheme {
    const provider = this.providerMap.get(key);
    if (!provider) {
      throw new Error(`Provider not found: ${key}`);
    }
    return provider;
  }
}
