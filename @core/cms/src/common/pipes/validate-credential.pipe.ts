// src/common/pipes/validate-credential.pipe.ts

import {
  PipeTransform,
  Injectable,
  BadRequestException,
  Logger,
} from "@nestjs/common";
import { CreateCredentialDto } from "../../database-integration/dto/create-credential.dto";
import { join } from "path";
import { readFileSync } from "fs";

/**
 * This pipe validates that the credential type is allowed for the given service.
 */
@Injectable()
export class ValidateCredentialPipe implements PipeTransform {
  transform(value: CreateCredentialDto) {
    Logger.log("Validating credential", "ValidateCredentialPipe");
    const { serviceName, credentialType } = value;

    // Get the allowed credential types for the given service.
    const filePath = join(__dirname, "../../config/allowedCredentials.json");
    const allowedCredentials = JSON.parse(readFileSync(filePath, "utf8"));
    const allowedTypes = allowedCredentials[serviceName];

    if (!allowedTypes || !allowedTypes.includes(credentialType)) {
      throw new BadRequestException(
        `Invalid credential type '${credentialType}' for service '${serviceName}'.`
      );
    }

    return value;
  }
}
