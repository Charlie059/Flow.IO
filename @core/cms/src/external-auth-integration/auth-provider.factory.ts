import { Injectable } from "@nestjs/common";
import { AuthString, AuthService } from "./@types";
import { GoogleV2OAuth2Service } from "./auth-providers/oauth2/google/v2/google.v2.service";
import { GithubV1OAuth2Service } from "./auth-providers/oauth2/github/v1/github.v1.service";
import { MixpanelV1BasicAuthService } from "./auth-providers/basic-auth/mixpanel/v1/mixpanel.v1.service";
import { OAuthString } from "./auth-providers/oauth2/@types";
import { IOAuth } from "./auth-providers/oauth2/interface/ioauth.interface";
import { BasicAuthString } from "./auth-providers/basic-auth/@types";
import { IBasicAuth } from "./auth-providers/basic-auth/interface/basic-auth.interface";

@Injectable()
export class AuthProviderFactory {
  // A map to hold the associations between authentication keys and their corresponding services
  private readonly providerMap = new Map<AuthString, AuthService>();

  constructor(
    private googleV2OAuth2Service: GoogleV2OAuth2Service,
    private githubV1OAuth2Service: GithubV1OAuth2Service,
    private mixpanelV1BasicAuthService: MixpanelV1BasicAuthService,
  ) {
    // Register available authentication providers
    this.registerProvider("oauth-google-v2", this.googleV2OAuth2Service);
    this.registerProvider("oauth-github-v1", this.githubV1OAuth2Service);
    this.registerProvider("basic-mixpanel-v1", this.mixpanelV1BasicAuthService);
  }

  // Function overloading for registerProvider to handle different types of auth providers
  private registerProvider(key: OAuthString, provider: IOAuth): void;
  private registerProvider(key: BasicAuthString, provider: IBasicAuth): void;
  private registerProvider(key: AuthString, provider: AuthService): void {
    // Register a provider with a specific key in the map
    this.providerMap.set(key, provider);
  }

  // Function overloading for getProvider to retrieve the appropriate type of auth provider
  public getProvider(key: OAuthString): IOAuth;
  public getProvider(key: BasicAuthString): IBasicAuth;
  public getProvider(key: AuthString): AuthService;

  public getProvider(key: AuthString): AuthService {
    // Retrieve the provider associated with the given key
    const provider = this.providerMap.get(key);
    if (!provider) {
      // If the provider is not found, throw an error
      throw new Error(`Authentication provider not found for key: ${key}`);
    }
    return provider;
  }
}
