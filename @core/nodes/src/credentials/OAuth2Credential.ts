/* eslint-disable @typescript-eslint/no-explicit-any */
// OAuth2Credential.ts

import { ICredential } from "../../common/interfaces/ICredential";

interface OAuth2Config {
  clientId: string;
  clientSecret: string;
  authEndpoint: string;
  tokenEndpoint: string;
  redirectUri: string;
  scopes: string[];
}

export class OAuth2Credential implements ICredential {
  private clientId: string;
  private clientSecret: string;
  private authEndpoint: string;
  private tokenEndpoint: string;
  private redirectUri: string;
  private scopes: string[];
  private accessToken?: string;
  private refreshToken?: string;

  constructor(config: OAuth2Config) {
    this.clientId = config.clientId;
    this.clientSecret = config.clientSecret;
    this.authEndpoint = config.authEndpoint;
    this.tokenEndpoint = config.tokenEndpoint;
    this.redirectUri = config.redirectUri;
    this.scopes = config.scopes;
  }

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  async getAccessToken(_code: string): Promise<void> {}

  async updateRefreshToken(): Promise<void> {}

  async validateToken?(): Promise<boolean> {
    return false;
  }

  async authenticate(requestOptions: any): Promise<any> {
    requestOptions.headers = { Authorization: `Bearer ${this.accessToken}` };
    return requestOptions;
  }

  getAccessTokenValue(): string | undefined {
    return this.accessToken;
  }
}
