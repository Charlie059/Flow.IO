import { Module } from "@nestjs/common";
import { HttpModule } from "@nestjs/axios";
import { EncryptionDecryptionModule } from "~/encryption-decryption/encryption-decryption.module";
import { GoogleV2Service } from "./google/v2/google.v2.service";
import { GoogleV2Config } from "./google/v2/google.v2.config";
import { AirtableV1Service } from "./airtable/v1/airtable.v1.service";
import { AirtableV1Config } from "./airtable/v1/airtable.v1.config";

@Module({
  imports: [EncryptionDecryptionModule, HttpModule],
  providers: [
    GoogleV2Service,
    {
      provide: "GoogleV2Config",
      useFactory: () => new GoogleV2Config().oAuth2Config,
    },
    AirtableV1Service,
    {
      provide: "AirtableV1Config",
      useFactory: () => new AirtableV1Config().oAuth2Config,
    },
  ],
  exports: [GoogleV2Service],
})
export class OAuth2Module {}
