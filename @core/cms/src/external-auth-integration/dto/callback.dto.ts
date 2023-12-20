import { CredentialProviderDto } from "./credential-provider.dto";

export class CallbackDto extends CredentialProviderDto {
  code?: string;
  data: any;
}
