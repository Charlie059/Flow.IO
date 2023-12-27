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
        client: {
          id: process.env.GOOGLE_OAUTH_V2_CLIENT_ID,
          secret: process.env.GOOGLE_OAUTH_V2_CLIENT_SECRET,
        },
        authUrl: {
          authorizeUrl: process.env.GOOGLE_OAUTH_V2_URL,
          tokenUrl: process.env.GOOGLE_OAUTH_TOKEN_URL,
        },
      },
      scope: ["https://www.googleapis.com/auth/drive"],
      callbackUri: process.env.GOOGLE_OAUTH_V2_REDIRECT_URI,
    };
  }
}
