import { Module } from "@nestjs/common";
import { GoogleOAuthV2Service } from "./google/v2/google.oauth.v2.service";
import { GoogleOAuthV2Config } from "./google/v2/google.v2.config";
import { EncryptionDecryptionModule } from "src/encryption-decryption/encryption-decryption.module";
import { HttpModule } from "@nestjs/axios";
@Module({
  imports: [EncryptionDecryptionModule, HttpModule],
  providers: [
    GoogleOAuthV2Service,
    GoogleOAuthV2Config,
    {
      provide: "GoogleOAuthV2Config",
      useFactory: () => new GoogleOAuthV2Config().oAuth2Config,
    },
  ],
  exports: [GoogleOAuthV2Service],
})
export class OAuth2Module {}
