import { Injectable } from "@nestjs/common";
import { AuthProviderFactory } from "./auth-provider.factory";

@Injectable()
export class ExternalAuthIntegrationService {
  constructor(private readonly authProviderFactory: AuthProviderFactory) {}

  async authenticate(providerKey: string): Promise<string> {
    const authProvider = this.authProviderFactory.getProvider(providerKey);
    if (!authProvider) {
      throw new Error(
        `Authentication provider not found for key: ${providerKey}`
      );
    }

    return await authProvider.authenticate();
  }

  async verifyToken(providerKey: string, token: string): Promise<any> {
    const authProvider = this.authProviderFactory.getProvider(providerKey);
    if (!authProvider) {
      throw new Error(
        `Authentication provider not found for key: ${providerKey}`
      );
    }

    return await authProvider.verifyToken(token);
  }

  async refreshToken(providerKey: string, refreshToken: string): Promise<any> {
    const authProvider = this.authProviderFactory.getProvider(providerKey);
    if (!authProvider) {
      throw new Error(
        `Authentication provider not found for key: ${providerKey}`
      );
    }

    return await authProvider.refreshToken(refreshToken);
  }
}
