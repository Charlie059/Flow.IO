import { Module } from "@nestjs/common";
import { EncryptionDecryptionService } from "./encryption-decryption.service";

@Module({})
export class EncryptionDecryptionModule {
  services: [EncryptionDecryptionService];
  exports: [EncryptionDecryptionService];
}
