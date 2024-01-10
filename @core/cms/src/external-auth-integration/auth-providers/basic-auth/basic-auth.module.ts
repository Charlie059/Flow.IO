import { Module } from "@nestjs/common";
import { HttpModule } from "@nestjs/axios";
import { EncryptionDecryptionModule } from "~/encryption-decryption/encryption-decryption.module";

@Module({
  imports: [EncryptionDecryptionModule, HttpModule],
  providers: [
    // GoogleV2OAuth2Service,
    // {
    //   provide: "GoogleV2OAuth2Config",
    //   useFactory: () => new GoogleV2OAuth2Config().oAuth2Config,
    // },
  ],
  // exports: [GoogleV2OAuth2Service],
})
export class BasicAuthModule {}
