import { Injectable } from "@nestjs/common";
import { IOAuth2Config } from "~/external-auth-integration/auth-providers/oauth2/@types";

/**
 * Service for configuring Github V1 OAuth2.
 */
@Injectable()
export class GithubV1OAuth2Config {
  public oAuth2Config: IOAuth2Config;

  constructor() {
    // Initialize the OAuth2 configuration object
    this.oAuth2Config = {
      credentials: {
        id: process.env.GITHUB_V1_OAUTH2_CLIENT_ID,
        secret: process.env.GITHUB_V1_OAUTH2_CLIENT_SECRET,
      },
      scope: [],
      provider: {
        authorizeUrl: "https://github.com/login/oauth/authorize",
        tokenUrl: "https://github.com/login/oauth/access_token",
        callbackUrl: `${process.env.HOST_CONFIG_URL}/oauth/callback`,
        verifyTokenUrl: "https://api.github.com",
        refreshTokenUrl: "https://github.com/login/oauth/access_token",
        tokenRefreshBuffer: 7776000, // 90 days
      },
    };
  }
}
