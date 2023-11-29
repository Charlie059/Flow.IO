// app.controller.ts
import { Controller, Logger, UsePipes } from "@nestjs/common";
import { MessagePattern } from "@nestjs/microservices";
import { DatabaseIntegrationService } from "./database-integration/database-integration.service";
import { EncryptionDecryptionService } from "./encryption-decryption/encryption-decryption.service";
import { CreateCredentialDto } from "./database-integration/dto/create-credential.dto";
import { ValidateCredentialPipe } from "./common/pipes/validate-credential.pipe";

@Controller()
export class AppController {
  constructor(
    private databaseIntegrationService: DatabaseIntegrationService,
    private encryptionDecryptionService: EncryptionDecryptionService
  ) {}

  @MessagePattern("create_credential")
  @UsePipes(new ValidateCredentialPipe())
  async handleCreateCredential(message: CreateCredentialDto) {
    Logger.log("Received data:", message, "AppController");
    return this.databaseIntegrationService.createCredential(message);
  }

  @MessagePattern("encrypt_data")
  async handleEncryptData(message: any) {
    const encrypt_data =
      await this.encryptionDecryptionService.encryptData("data");
    console.log("encrypt_data", encrypt_data);

    // Store the data to the KV store
    const credentialId = "111";
    await this.encryptionDecryptionService.storeData(
      credentialId,
      encrypt_data
    );

    // Retrieve the data from the KV store
    const data =
      await this.encryptionDecryptionService.retrieveData(credentialId);

    // Decrypt the data
    const decrypt_data =
      await this.encryptionDecryptionService.decryptData(data);

    return encrypt_data;
  }
}
