// src/database-integration/dto/create-credential.dto.ts

export class CreateCredentialDto {
  userId: number;
  serviceName: string;
  nodeAccessIds: number[];
  credentialType: string;
  encryptedCredential: string;
}
