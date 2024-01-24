/* eslint-disable @typescript-eslint/no-unused-vars */
// app.controller.ts
import { Controller, Logger, UsePipes } from "@nestjs/common";
import { MessagePattern } from "@nestjs/microservices";
import { DatabaseIntegrationService } from "./database-integration/database-integration.service";
import { EncryptionDecryptionService } from "./encryption-decryption/encryption-decryption.service";
import { CreateCredentialDto } from "./database-integration/dto/create-credential.dto";
import { ValidateCredentialPipe } from "./common/pipes/validate-credential.pipe";
import { ExternalAuthIntegrationService } from "./external-auth-integration/external-auth-integration.service";

import { BasicAuthDto } from "./dto/basic-auth.dto";
import { BasicAuthVerifyDto } from "./dto/basic-auth-verify.dto";

@Controller()
export class AppController {
  constructor(
    private databaseIntegrationService: DatabaseIntegrationService,
    private encryptionDecryptionService: EncryptionDecryptionService,
    private externalAuthIntegrationService: ExternalAuthIntegrationService,
  ) {}

  private readonly logger = new Logger(AppController.name);

  // TODO: Add DTO
  @MessagePattern("create_credential")
  @UsePipes(new ValidateCredentialPipe())
  async handleCreateCredential(message: CreateCredentialDto) {
    this.logger.log(`Received data: ${message}`);
    return this.databaseIntegrationService.createCredential(message);
  }

  // TODO: Add DTO
  @MessagePattern("encrypt_data")
  async handleEncryptData(message: any) {
    const encrypt_data = await this.encryptionDecryptionService.encryptData("data");
    console.log("encrypt_data", encrypt_data);

    // Store the data to the KV store
    const credentialId = "123";
    await this.encryptionDecryptionService.storeData(credentialId, encrypt_data);

    // Retrieve the data from the KV store
    const data = await this.encryptionDecryptionService.retrieveData(credentialId);

    // Decrypt the data
    const decrypt_data = await this.encryptionDecryptionService.decryptData(data);

    console.log("decrypt_data", decrypt_data);

    return encrypt_data;
  }

  // TODO: Add DTO
  @MessagePattern("oauth")
  async handleOAuth(message: any) {
    // TODO: Add JWT and pass params
    const url = this.externalAuthIntegrationService.authenticate(message);
    return url;
  }

  // TODO: Add DTO
  @MessagePattern("oauth_verify_token")
  async handleOAuthVerifyToken(message: any) {
    const token = message.token;
    const providerKey = message.providerKey;
    const data = await this.externalAuthIntegrationService.verifyToken(providerKey, token);
    this.logger.log(`Received data: ${JSON.stringify(data)}`);
    return data;
  }

  // TODO: Add DTO
  @MessagePattern("oauth_refresh_token")
  async handleOAuthRefreshToken(message: any) {
    const refreshToken = message.refreshToken;
    const providerKey = message.providerKey;
    const data = await this.externalAuthIntegrationService.refreshToken(providerKey, refreshToken);
    return data;
  }

  // TODO: Add DTO
  @MessagePattern("basic_auth")
  async handleBasicAuth(message: BasicAuthDto) {
    // TODO: Add JWT

    const providerKey = message.providerKey;
    return await this.externalAuthIntegrationService.authenticate(providerKey);
  }

  // TODO: Add DTO
  @MessagePattern("basic_auth_verify")
  async handleBasicAuthVerify(message: BasicAuthVerifyDto) {
    // TODO: Add JWT

    const providerKey = message.providerKey;
    return await this.externalAuthIntegrationService.verifyBasic(providerKey);
  }
}
