// src/database-integration/dto/create-credential.dto.ts

export class CreateCredentialDto {
  userId: number;
  serviceNameId: number;
  credentialTypeId: number;
  data: any;
}
