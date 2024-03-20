import { Injectable } from "@nestjs/common";
import { IOAuth2Config } from "~/external-auth-integration/auth-providers/oauth2/@types";

/**
 * Service for configuring Zoom OAuth2.
 */
@Injectable()
export class ZoomOAuth2Config {
  public oAuth2Config: IOAuth2Config;

  constructor() {
    // Initialize the OAuth2 configuration object
    this.oAuth2Config = {
      credentials: {
        id: process.env.ZOOM_OAUTH2_CLIENT_ID,
        secret: process.env.ZOOM_OAUTH2_CLIENT_SECRET,
      },
      scope: [],
      provider: {
        authorizeUrl: "https://zoom.us/oauth/authorize",
        tokenUrl: "https://zoom.us/oauth/token",
        callbackUrl: `${process.env.HOST_CONFIG_URL}/oauth/callback`,
        verifyTokenUrl: "",
        refreshTokenUrl: "",
        tokenRefreshBuffer: 7776000, // 90 days
      },
    };
  }
}
