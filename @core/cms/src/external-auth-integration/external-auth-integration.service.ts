import { BadRequestException, Injectable } from "@nestjs/common";
import { CredentialProviderDto } from "./dto/credential-provider.dto";
import { ProviderFactory } from "./providers/provider-factory";

@Injectable()
export class ExternalAuthIntegrationService {

  constructor() {}

  async requestAuth(data: CredentialProviderDto): Promise<string> {
    return ProviderFactory.getProvider(data.provider).getAuthUrl(data.userId);
  }

  async callback(data: CredentialProviderDto) {

  }
}
