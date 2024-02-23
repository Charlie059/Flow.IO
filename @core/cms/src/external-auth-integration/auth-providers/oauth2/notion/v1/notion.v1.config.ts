import { Injectable } from "@nestjs/common";
import { IOAuth2Config } from "~/external-auth-integration/auth-providers/oauth2/@types";

/**
 * Service for configuring Slack V2 OAuth2.
 */
@Injectable()
export class NotionV1OAuth2Config {
  public oAuth2Config: IOAuth2Config;

  constructor() {
    this.oAuth2Config = {
      credentials: {
        id: process.env.NOTION_V1_OAUTH2_CLIENT_ID,
        secret: process.env.NOTION_V1_OAUTH2_CLIENT_SECRET,
      },
      scope: [],
      provider: {
        authorizeUrl: "https://api.notion.com/v1/oauth/authorize",
        tokenUrl: "https://api.notion.com/v1/oauth/token",
        callbackUrl: `${process.env.HOST_CONFIG_URL}/oauth/callback`,
      },
    };
  }
}
