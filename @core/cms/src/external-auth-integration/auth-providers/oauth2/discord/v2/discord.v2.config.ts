import { Injectable } from "@nestjs/common";
import { IOAuth2Config } from "~/external-auth-integration/auth-providers/oauth2/@types";

@Injectable()
export class DiscordV2OAuth2Config {
  public oAuth2Config: IOAuth2Config;

  constructor() {
    this.oAuth2Config = {
      credentials: {
        id: process.env.DISCORD_V2_OAUTH2_CLIENT_ID,
        secret: process.env.DISCORD_V2_OAUTH2_CLIENT_SECRET,
      },
      scope: ["identify", "email"],
      provider: {
        authorizeUrl: "https://discord.com/api/oauth2/authorize",
        tokenUrl: "https://discord.com/api/oauth2/token",
        callbackUrl: `${process.env.HOST_CONFIG_URL}/oauth/callback`,
        verifyTokenUrl: "https://discord.com/api/oauth2/token",
        refreshTokenUrl: "https://discord.com/api/oauth2/token",
        tokenRefreshBuffer: 43200,
      },
    };
  }
}
