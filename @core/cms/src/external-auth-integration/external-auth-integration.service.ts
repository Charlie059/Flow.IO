import { Injectable } from "@nestjs/common";
import { AuthProviderFactory } from "./auth-provider.factory";
import { AuthString } from "./@types";
import { OAuthString } from "./auth-providers/oauth2/@types";
import { BasicAuthString } from "./auth-providers/basic-auth/@types";

@Injectable()
export class ExternalAuthIntegrationService {
  constructor(private readonly authProviderFactory: AuthProviderFactory) {}

  async authenticate(providerKey: AuthString): Promise<any> {
    const authProvider = this.authProviderFactory.getProvider(providerKey);
    if (!authProvider) {
      throw new Error(`Authentication provider not found for key: ${providerKey}`);
    }

    return await authProvider.authenticate();
  }

  async verifyToken(providerKey: OAuthString, token: string): Promise<any> {
    const authProvider = this.authProviderFactory.getProvider(providerKey);
    if (!authProvider) {
      throw new Error(`Authentication provider not found for key: ${providerKey}`);
    }

    return await authProvider.verifyToken(token);
  }

  async refreshToken(providerKey: OAuthString, refreshToken: string): Promise<any> {
    const authProvider = this.authProviderFactory.getProvider(providerKey);
    if (!authProvider) {
      throw new Error(`Authentication provider not found for key: ${providerKey}`);
    }

    return await authProvider.refreshToken(refreshToken);
  }

  async verifyBasic(providerKey: BasicAuthString): Promise<any> {
    const authProvider = this.authProviderFactory.getProvider(providerKey);
    if (!authProvider) {
      throw new Error(`Authentication provider not found for key: ${providerKey}`);
    }

    return await authProvider.verify();
  }
}
