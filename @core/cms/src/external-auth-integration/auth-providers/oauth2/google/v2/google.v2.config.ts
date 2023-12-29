import { Injectable } from "@nestjs/common";
import { IOAuth2Config } from "../../@types";

/**
 * Service for configuring Google OAuth2.
 */
@Injectable()
export class GoogleOAuthV2Config {
  public oAuth2Config: IOAuth2Config;

  constructor() {
    // Initialize the OAuth2 configuration object
    this.oAuth2Config = {
      credentials: {
        id: process.env.GOOGLE_OAUTH_V2_CLIENT_ID,
        secret: process.env.GOOGLE_OAUTH_V2_CLIENT_SECRET,
      },
      scope: ["https://www.googleapis.com/auth/drive"],
      provider: {
        authorizeUrl: process.env.GOOGLE_OAUTH_V2_URL,
        tokenUrl: process.env.GOOGLE_OAUTH_TOKEN_URL,
        callbackUrl: process.env.GOOGLE_OAUTH_V2_REDIRECT_URI,
        verifyTokenUrl: process.env.GOOGLE_OAUTH_V2_VALIDATE_URL,
        refreshTokenUrl: "https://accounts.google.com/o/oauth2/token",
        tokenRefreshBuffer: 7776000, // 90 days
        callbackUriParams: {
          response_type: "code",
          access_type: "offline",
        },
      },
    };
  }
}
