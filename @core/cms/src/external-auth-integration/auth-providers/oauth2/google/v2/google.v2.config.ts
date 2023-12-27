import { Injectable } from "@nestjs/common";

@Injectable()
export class GoogleOAuthV2Config {
  clientId: string;
  clientSecret: string;
  redirectUri: string;
  authUrl: string;
  scope: string[];

  constructor() {
    this.clientId = process.env.GOOGLE_OAUTH_V2_CLIENT_ID;
    this.clientSecret = process.env.GOOGLE_OAUTH_V2_CLIENT_SECRET;
    this.redirectUri = process.env.GOOGLE_OAUTH_V2_REDIRECT_URI;
    this.authUrl = process.env.GOOGLE_OAUTH_V2_URL;
    this.scope = ["https://www.googleapis.com/auth/drive"];
  }
}
