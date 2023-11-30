import { Injectable } from "@nestjs/common";
import { OAuthService } from "./oauth2.service";

@Injectable()
export class ExternalAuthIntegrationService {
  constructor(
    //TODO: Change any to the correct type
    private oAuthService: OAuthService
  ) {}

  authenticate(provider: string): string {
    return this.oAuthService.authenticate(provider);
  }
}
