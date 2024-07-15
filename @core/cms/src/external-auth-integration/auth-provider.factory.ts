import { Injectable } from "@nestjs/common";
import { IOAuth } from "./auth-providers/oauth2/interface/ioauth.interface";
import { GoogleV2OAuth2Service } from "./auth-providers/oauth2/google/v2/google.v2.service";
import { GithubV1OAuth2Service } from "./auth-providers/oauth2/github/v1/github.v1.service";
import { AirtableV1OAuth2Service } from "./auth-providers/oauth2/airtable/v1/airtable.v1.service";
import { SlackV2OAuth2Service } from "./auth-providers/oauth2/slack/v2/slack.v2.service";
import { StripeOauth2Service } from "./auth-providers/oauth2/stripe/v1/stripe.service";
import { ZoomOAuth2Service } from "./auth-providers/oauth2/zoom/zoom.service";
import { AwsV2OAuth2Service } from "./auth-providers/oauth2/aws/v2/aws.v2.service";
import { DiscordV2OAuth2Service } from "./auth-providers/oauth2/discord/v2/discord.v2.service";
import { FigmaV2OAuth2Service } from "./auth-providers/oauth2/figma/v2/figma.v2.service";

@Injectable()
export class AuthProviderFactory {
  private readonly providerMap = new Map<string, IOAuth>();

  constructor(
    private googleV2OAuth2Service: GoogleV2OAuth2Service,
    private githubV1OAuth2Service: GithubV1OAuth2Service,
    private airtableV1OAuth2Service: AirtableV1OAuth2Service,
    private slackV2OAuth2Service: SlackV2OAuth2Service,
    private stripeOAuth2Service: StripeOauth2Service,
    private zoomOAuth2Service: ZoomOAuth2Service
    private awsV2OAuth2Service: AwsV2OAuth2Service,
    private discordV2OAuth2Service: DiscordV2OAuth2Service,
    private figmaV2OAuth2Service: FigmaV2OAuth2Service,
  ) {
    this.registerProvider("oauth-google-v2", this.googleV2OAuth2Service);
    this.registerProvider("oauth-github-v1", this.githubV1OAuth2Service);
    this.registerProvider("oauth-airtable-v1", this.airtableV1OAuth2Service);
    this.registerProvider("oauth-slack-v2", this.slackV2OAuth2Service);
    this.registerProvider("oauth-stripe-v2", this.stripeOAuth2Service);
    this.registerProvider("oauth-zoom-v1", this.zoomOAuth2Service);
    this.registerProvider("oauth-aws-v2", this.awsV2OAuth2Service);
    this.registerProvider("oauth-discord-v2", this.discordV2OAuth2Service);
    this.registerProvider("oauth-figma-v2", this.figmaV2OAuth2Service);
  }

  private registerProvider(key: string, provider: IOAuth) {
    this.providerMap.set(key, provider);
  }

  public getProvider(key: string): IOAuth {
    const provider = this.providerMap.get(key);
    if (!provider) {
      throw new Error(`Provider not found: ${key}`);
    }
    return provider;
  }
}
