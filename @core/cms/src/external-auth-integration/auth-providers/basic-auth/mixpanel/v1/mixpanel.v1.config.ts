import { Injectable } from "@nestjs/common";
import { BasicAuthConfig } from "../../@types";

/**
 * Service for configuring Mixpanel V1 Basic HTTP Authentication.
 */
@Injectable()
export class MixpanelV1BasicAuthConfig {
  public basicAuthConfig: BasicAuthConfig;

  constructor() {
    this.basicAuthConfig = {
      provider: {
        authenticateUrl: "https://mixpanel.com/api/app/me",
        verifyUrl: "https://mixpanel.com/api/app/organizations/{organizationId}/service-accounts",
      },
      credential: { username: process.env.MIXPANEL_V1_BASIC_USERNAME, password: process.env.MIXPANEL_V1_BASIC_PASSWORD },
    };
  }
}
