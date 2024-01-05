import { Injectable } from "@nestjs/common";
import { IOAuth2Config } from "~/external-auth-integration/auth-providers/oauth2/@types";

/**
 * Service for configuring Airtable V1 OAuth2.
 */
@Injectable()
export class AirtableV1OAuth2Config {
  public oAuth2Config: IOAuth2Config;

  constructor() {
    this.oAuth2Config = {
      credentials: {
        id: process.env.AIRTABLE_V1_OAUTH2_CLIENT_ID,
        secret: process.env.AIRTABLE_V1_OAUTH2_CLIENT_SECRET,
      },
      scope: [], // TODO: Airtable scopes
      provider: {
        authorizeUrl: "https://airtable.com/oauth2/v1/authorize",
        tokenUrl: "https://airtable.com/oauth2/v1/token",
        callbackUrl: `${process.env.HOST_CONFIG_URL}/oauth/callback`,
        verifyTokenUrl: "https://airtable.com/oauth2/v1/token",
        refreshTokenUrl: "https://airtable.com/oauth2/v1/token",
        tokenRefreshBuffer: 5184000, // 60 days
        callbackUrlParams: {
          response_type: "code",
        },
      },
    };
  }
}
