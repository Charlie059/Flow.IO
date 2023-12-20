import { Module } from "@nestjs/common";
import { ExternalAuthIntegrationService } from "./services/external-auth-integration.service";
import { OAuthService as OAuth2Service } from "./services/oauth2.service";
import { OAuth2Controller } from "./controller/oauth2.controller";

@Module({
  providers: [ExternalAuthIntegrationService, OAuth2Service],
  controllers: [OAuth2Controller],
  exports: [ExternalAuthIntegrationService, OAuth2Service],
})
export class ExternalAuthIntegrationModule {}
