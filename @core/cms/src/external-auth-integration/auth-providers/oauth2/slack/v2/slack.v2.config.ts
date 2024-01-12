import { Injectable } from "@nestjs/common";
import { IOAuth2Config } from "~/external-auth-integration/auth-providers/oauth2/@types";

/**
 * Service for configuring Slack V2 OAuth2.
 */
@Injectable()
export class SlackV2OAuth2Config {
  public oAuth2Config: IOAuth2Config;

  constructor() {
    this.oAuth2Config = {
      credentials: {
        id: process.env.SLACK_V2_OAUTH2_CLIENT_ID,
        secret: process.env.SLACK_V2_OAUTH2_CLIENT_SECRET,
      },
      scope: ["email"], // TODO: Slack scopes
      provider: {
        authorizeUrl: "https://slack.com/oauth/v2/authorize",
        tokenUrl: "https://slack.com/api/oauth.v2.exchange",
        callbackUrl: `${process.env.HOST_CONFIG_URL}/oauth/callback`,
        verifyTokenUrl: "https://slack.com/api/auth.test",
        refreshTokenUrl: "https://slack.com/api/oauth.v2.exchange",
        tokenRefreshBuffer: 43200, // 12 hours
        callbackUrlParams: {
          response_type: "code",
          access_type: "offline",
        },
      },
    };
  }
}
