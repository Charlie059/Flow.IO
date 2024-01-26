import { Module } from "@nestjs/common";
import { HttpModule } from "@nestjs/axios";
import { EncryptionDecryptionModule } from "~/encryption-decryption/encryption-decryption.module";
import { MixpanelV1BasicAuthService } from "./mixpanel/v1/mixpanel.v1.service";
import { MixpanelV1BasicAuthConfig } from "./mixpanel/v1/mixpanel.v1.config";

@Module({
  imports: [HttpModule, EncryptionDecryptionModule],
  providers: [
    MixpanelV1BasicAuthService,
    {
      provide: "MixpanelV1BasicAuthConfig",
      useFactory: () => new MixpanelV1BasicAuthConfig().basicAuthConfig,
    },
  ],
  exports: [MixpanelV1BasicAuthService],
})
export class BasicAuthModule {}
