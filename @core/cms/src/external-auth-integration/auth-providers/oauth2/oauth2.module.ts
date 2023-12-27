import { Module } from "@nestjs/common";
import { GoogleOAuthV2Service } from "./google/v2/google.oauth.v2.service";
import { GoogleOAuthV2Config } from "./google/v2/google.v2.config";
import { EncryptionDecryptionModule } from "src/encryption-decryption/encryption-decryption.module";

@Module({
  imports: [EncryptionDecryptionModule],
  providers: [GoogleOAuthV2Service, GoogleOAuthV2Config],
  exports: [GoogleOAuthV2Service],
})
export class OAuth2Module {}
