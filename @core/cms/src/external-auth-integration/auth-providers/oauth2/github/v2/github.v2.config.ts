import { Injectable } from "@nestjs/common";
import { IOAuth2Config } from "../../@types";

/**
 * Service for configuring Github OAuth2.
 */
@Injectable()
export class GithubOAuthV2Config {
  public oAuth2Config: IOAuth2Config;

  constructor() {
    // Initialize the OAuth2 configuration object
    this.oAuth2Config = {
      credentials: {
        id: process.env.GITHUB_OAUTH_V2_CLIENT_ID,
        secret: process.env.GITHUB_OAUTH_V2_CLIENT_SECRET,
      },
      scope: [],
      provider: {
        authorizeUrl: "https://github.com/login/oauth/authorize",
        tokenUrl: "https://github.com/login/oauth/access_token",
        callbackUrl: "https://localhost:3000/oauth/callback",
        verifyTokenUrl: "https://api.github.com",
        refreshTokenUrl: "https://github.com/login/oauth/access_token",
        tokenRefreshBuffer: 7776000, // 90 days
      },
    };
  }
}
