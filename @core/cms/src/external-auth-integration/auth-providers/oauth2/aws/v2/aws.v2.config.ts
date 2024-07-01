import { Injectable } from "@nestjs/common";
import { IOAuth2Config } from "~/external-auth-integration/auth-providers/oauth2/@types";

/**
 * Service for configuring Slack V2 OAuth2.
 */
@Injectable()
export class AwsckV2OAuth2Config {
  public oAuth2Config: IOAuth2Config;

  constructor() {
    this.oAuth2Config = {
      credentials: {
        id: process.env.AWS_V2_OAUTH2_CLIENT_ID,
        secret: process.env.AWS_V2_OAUTH2_CLIENT_SECRET,
      },
      scope: ["openid", "email", "profile", "aws.cognito.signin.user.admin"],
      provider: {
        authorizeUrl: "https://myflowio.auth.us-east-2.amazoncognito.com/oauth2/authorize",
        tokenUrl: "https://myflowio.auth.us-east-2.amazoncognito.com/oauth2/token",
        callbackUrl: `${process.env.HOST_CONFIG_URL}/oauth/callback`,
        verifyTokenUrl: "https://myflowio.auth.us-east-2.amazoncognito.com/oauth2/userInfo",
        refreshTokenUrl: "https://myflowio.auth.us-east-2.amazoncognito.com/oauth2/token",
        tokenRefreshBuffer: 43200,
      },
    };
  }
}
