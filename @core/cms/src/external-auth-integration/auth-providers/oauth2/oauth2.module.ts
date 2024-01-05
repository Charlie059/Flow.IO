import { Module } from "@nestjs/common";
import { HttpModule } from "@nestjs/axios";
import { EncryptionDecryptionModule } from "~/encryption-decryption/encryption-decryption.module";
import { GoogleV2OAuth2Service } from "./google/v2/google.v2.service";
import { GoogleV2OAuth2Config } from "./google/v2/google.v2.config";
import { GithubV1OAuth2Service } from "./github/v1/github.v1.service";
import { GithubV1OAuth2Config } from "./github/v1/github.v1.config";

@Module({
  imports: [EncryptionDecryptionModule, HttpModule],
  providers: [
    GoogleV2OAuth2Service,
    {
      provide: "GoogleV2OAuthV2Config",
      useFactory: () => new GoogleV2OAuth2Config().oAuth2Config,
    },
    GithubV1OAuth2Service,
    {
      provide: "GithubOAuthV2Config",
      useFactory: () => new GithubV1OAuth2Config().oAuth2Config,
    },
  ],
  exports: [GoogleV2OAuth2Service, GithubV1OAuth2Service],
})
export class OAuth2Module {}
