import { Module } from "@nestjs/common";
import { EncryptionDecryptionService } from "./encryption-decryption.service";

@Module({
  providers: [EncryptionDecryptionService],
  exports: [EncryptionDecryptionService],
})
export class EncryptionDecryptionModule {}
