import { Module } from "@nestjs/common";
import { GoogleOAuthV2Service } from "./google/v2/google.oauth.v2.service";
import { GithubOAuthV2Service } from "./github/v2/github.oauth.v2.service";
import { GoogleOAuthV2Config } from "./google/v2/google.v2.config";
import { GithubOAuthV2Config } from "./github/v2/github.v2.config";
import { EncryptionDecryptionModule } from "src/encryption-decryption/encryption-decryption.module";
import { HttpModule } from "@nestjs/axios";
import { EncryptionDecryptionModule } from "~/encryption-decryption/encryption-decryption.module";
import { GoogleV2Service } from "~/external-auth-integration/auth-providers/oauth2/google/v2/google.v2.service";
import { GoogleV2Config } from "~/external-auth-integration/auth-providers/oauth2/google/v2/google.v2.config";

@Module({
  imports: [EncryptionDecryptionModule, HttpModule],
  providers: [
    GoogleV2Service,
    GithubOAuthV2Service,
    {
      provide: "GoogleV2Config",
      useFactory: () => new GoogleV2Config().oAuth2Config,
    },
    {
      provide: "GithubOAuthV2Config",
      useFactory: () => new GithubOAuthV2Config().oAuth2Config,
    },
    {
      provide: "GithubOAuthV2Config",
      useFactory: () => new GithubOAuthV2Config().oAuth2Config,
    },
  ],
  exports: [GoogleOAuthV2Service],
})
export class OAuth2Module {}
