import { Module } from "@nestjs/common";
import { HttpModule } from "@nestjs/axios";
import { EncryptionDecryptionModule } from "~/encryption-decryption/encryption-decryption.module";
import { GoogleV2Service } from "~/external-auth-integration/auth-providers/oauth2/google/v2/google.v2.service";
import { GoogleV2Config } from "~/external-auth-integration/auth-providers/oauth2/google/v2/google.v2.config";

@Module({
  imports: [EncryptionDecryptionModule, HttpModule],
  providers: [
    GoogleV2Service,
    {
      provide: "GoogleV2Config",
      useFactory: () => new GoogleV2Config().oAuth2Config,
    },
  ],
  exports: [GoogleV2Service],
})
export class OAuth2Module {}
