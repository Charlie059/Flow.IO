import { Injectable } from "@nestjs/common";
import { CredentialProviderDto } from "./dto/credential-provider.dto";
import { ProviderFactory } from "./providers/provider-factory";
import { CallbackDto } from "./dto/callback.dto";

@Injectable()
export class ExternalAuthIntegrationService {

  constructor() {}

  async requestAuth(data: CredentialProviderDto): Promise<string> {
    return ProviderFactory.getProvider(data.provider).getAuthUrl(data.userId);
  }

  async handleCallback(data: CallbackDto) {
    return ProviderFactory.getProvider(data.provider).handleCallback(data);
  }
}
