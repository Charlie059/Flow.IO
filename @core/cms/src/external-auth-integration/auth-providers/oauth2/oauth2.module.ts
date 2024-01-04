import { Module } from "@nestjs/common";
import { GoogleOAuthV2Service } from "./google/v2/google.oauth.v2.service";
import { GithubOAuthV2Service } from "./github/v2/github.oauth.v2.service";
import { GoogleOAuthV2Config } from "./google/v2/google.v2.config";
import { GithubOAuthV2Config } from "./github/v2/github.v2.config";
import { EncryptionDecryptionModule } from "src/encryption-decryption/encryption-decryption.module";
import { HttpModule } from "@nestjs/axios";
@Module({
  imports: [EncryptionDecryptionModule, HttpModule],
  providers: [
    GoogleOAuthV2Service,
    GithubOAuthV2Service,
    GoogleOAuthV2Config,
    {
      provide: "GoogleOAuthV2Config",
      useFactory: () => new GoogleOAuthV2Config().oAuth2Config,
    },
    {
      provide: "GithubOAuthV2Config",
      useFactory: () => new GithubOAuthV2Config().oAuth2Config,
    },
  ],
  exports: [GoogleOAuthV2Service, GithubOAuthV2Service],
})
export class OAuth2Module {}
