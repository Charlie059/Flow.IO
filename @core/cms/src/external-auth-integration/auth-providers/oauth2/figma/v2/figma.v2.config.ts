import { Injectable } from "@nestjs/common";
import { IOAuth2Config } from "~/external-auth-integration/auth-providers/oauth2/@types";

@Injectable()
export class FigmaV2OAuth2Config {
  public oAuth2Config: IOAuth2Config;

  constructor() {
    this.oAuth2Config = {
      credentials: {
        id: process.env.FIGMA_V2_OAUTH2_CLIENT_ID,
        secret: process.env.FIGMA_V2_OAUTH2_CLIENT_SECRET,
      },
      scope: ["file_read"],
      provider: {
        authorizeUrl: "https://www.figma.com/oauth",
        tokenUrl: "https://www.figma.com/api/oauth/token",
        callbackUrl: `${process.env.HOST_CONFIG_URL}/oauth/callback`,
        verifyTokenUrl: "https://www.figma.com/api/oauth/token",
        refreshTokenUrl: "https://www.figma.com/api/oauth/token",
        tokenRefreshBuffer: 43200,
      },
    };
  }
}
