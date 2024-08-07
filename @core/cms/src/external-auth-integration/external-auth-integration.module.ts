import { Module } from "@nestjs/common";
import { EncryptionDecryptionModule } from "~/encryption-decryption/encryption-decryption.module";
import { AuthProviderFactory } from "./auth-provider.factory";
import { AuthCallbackController } from "./auth-callback.controller";
import { OAuth2Module } from "./auth-providers/oauth2/oauth2.module";
import { ExternalAuthIntegrationService } from "./external-auth-integration.service";

@Module({
  imports: [OAuth2Module, EncryptionDecryptionModule],
  providers: [AuthProviderFactory, ExternalAuthIntegrationService],
  controllers: [AuthCallbackController],
  exports: [ExternalAuthIntegrationService],
})
export class ExternalAuthIntegrationModule {}
