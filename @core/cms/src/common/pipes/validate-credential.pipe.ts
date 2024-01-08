import { PipeTransform, Injectable, BadRequestException } from "@nestjs/common";
import { CreateCredentialDto } from "../../database-integration/dto/create-credential.dto";
import * as path from "path";

// Dynamically load the JSON file
const allowedCredentialsPath = path.join(process.cwd(), "src/config/allowedCredentials.json");
// eslint-disable-next-line @typescript-eslint/no-var-requires
const allowedCredentials = require(allowedCredentialsPath);

@Injectable()
export class ValidateCredentialPipe implements PipeTransform {
  transform(value: CreateCredentialDto) {
    const { serviceName, credentialType } = value;

    const allowedTypes = allowedCredentials[serviceName];

    if (!allowedTypes || !allowedTypes.includes(credentialType)) {
      throw new BadRequestException(`Invalid credential type '${credentialType}' for service '${serviceName}'.`);
    }

    return value;
  }
}
