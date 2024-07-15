import { Module } from "@nestjs/common";
import { HttpModule } from "@nestjs/axios";
import { CacheModule } from "@nestjs/cache-manager";
import { EncryptionDecryptionModule } from "~/encryption-decryption/encryption-decryption.module";
import { GoogleV2OAuth2Service } from "./google/v2/google.v2.service";
import { GoogleV2OAuth2Config } from "./google/v2/google.v2.config";
import { GithubV1OAuth2Service } from "./github/v1/github.v1.service";
import { GithubV1OAuth2Config } from "./github/v1/github.v1.config";
import { AirtableV1OAuth2Service } from "./airtable/v1/airtable.v1.service";
import { AirtableV1OAuth2Config } from "./airtable/v1/airtable.v1.config";
import { SlackV2OAuth2Service } from "./slack/v2/slack.v2.service";
import { SlackV2OAuth2Config } from "./slack/v2/slack.v2.config";
import { StripeOauth2Service } from "./stripe/v1/stripe.service";
import { StripeV2OAuth2Config } from "./stripe/v1/stripe.config";
import { NotionV1OAuth2Service } from "./notion/v1/notion.v1.service";
import { NotionV1OAuth2Config } from "./notion/v1/notion.v1.config";
import { AwsV2OAuth2Service } from "./aws/v2/aws.v2.service";
import { AwsV2OAuth2Config } from "./aws/v2/aws.v2.config";
import { DiscordV2OAuth2Service } from "./discord/v2/discord.v2.service";
import { DiscordV2OAuth2Config } from "./discord/v2/discord.v2.config";
import { FigmaV2OAuth2Service } from "./figma/v2/figma.v2.service";
import { FigmaV2OAuth2Config } from "./figma/v2/figma.v2.config";

@Module({
  imports: [EncryptionDecryptionModule, HttpModule, CacheModule.register()],
  providers: [
    GoogleV2OAuth2Service,
    {
      provide: "GoogleV2OAuth2Config",
      useFactory: () => new GoogleV2OAuth2Config().oAuth2Config,
    },
    GithubV1OAuth2Service,
    {
      provide: "GithubV1OAuth2Config",
      useFactory: () => new GithubV1OAuth2Config().oAuth2Config,
    },
    AirtableV1OAuth2Service,
    {
      provide: "AirtableV1OAuth2Config",
      useFactory: () => new AirtableV1OAuth2Config().oAuth2Config,
    },
    SlackV2OAuth2Service,
    {
      provide: "SlackV2OAuth2Config",
      useFactory: () => new SlackV2OAuth2Config().oAuth2Config,
    },
    StripeOauth2Service,
    {
      provide: "StripeOAuth2Config",
      useFactory: () => new StripeV2OAuth2Config().oAuth2Config,
    },
    NotionV1OAuth2Service,
    {
      provide: "NotionV1OAuth2Config",
      useFactory: () => new NotionV1OAuth2Config().oAuth2Config,
    },
    AwsV2OAuth2Service,
    {
      provide: "AwsV2OAuth2Config",
      useFactory: () => new AwsV2OAuth2Config().oAuth2Config,
    },
    DiscordV2OAuth2Service,
    {
      provide: "DiscordV2OAuth2Config",
      useFactory: () => new DiscordV2OAuth2Config().oAuth2Config,
    },
    FigmaV2OAuth2Service,
    {
      provide: "FigmaV2OAuth2Config",
      useFactory: () => new FigmaV2OAuth2Config().oAuth2Config,
    },
  ],
  exports: [
    GoogleV2OAuth2Service,
    GithubV1OAuth2Service,
    AirtableV1OAuth2Service,
    SlackV2OAuth2Service,
    StripeOauth2Service,
    AwsV2OAuth2Service,
    DiscordV2OAuth2Service,
    FigmaV2OAuth2Service,
    NotionV1OAuth2Service
  ],
})
export class OAuth2Module {}
