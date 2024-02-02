import { Injectable } from "@nestjs/common";
import { IOAuth2Config } from "~/external-auth-integration/auth-providers/oauth2/@types";

/**
 * Service for configuring Slack V2 OAuth2.
 */
@Injectable()
export class StripeV2OAuth2Config {
  public oAuth2Config: IOAuth2Config;

  constructor() {
    this.oAuth2Config = {
      credentials: {
        id: process.env.STRIPE_OAUTH_V2_CLIENT_ID,
        secret: process.env.STRIPE_OAUTH_V2_CLIENT_SECRET,
      },
      scope: ["read_write"],
      provider: {
        authorizeUrl: "https://connect.stripe.com/oauth/authorize",
        tokenUrl: "https://connect.stripe.com/oauth/token",
        callbackUrl: `${process.env.HOST_CONFIG_URL}/oauth/callback/stripe`,
        verifyTokenUrl: "",
        refreshTokenUrl: "",
        tokenRefreshBuffer: 43200, // 12 hours
      },
    };
  }
}
