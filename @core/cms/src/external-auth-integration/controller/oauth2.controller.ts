// oauth.controller.ts

import { Controller, Get, Logger, Query, Redirect, Res } from "@nestjs/common";

@Controller("oauth2")
export class OAuth2Controller {
  constructor() {}

  @Get("callback")
  async handleGoogleCallback(@Query("code") code: string, @Res() res) {
    Logger.log(`Received code ${code}`, "OAuthController");
    res.status(200).send(`Received code: ${code}`);
  }
}
