import { Injectable } from "@nestjs/common";
import * as crypto from "crypto";

@Injectable()
export class EncryptionDecryptionService {
  private readonly algorithm = "aes-256-ctr";
  private readonly secretKey: string = process.env.SECRET_KEY;
  private readonly iv: string = process.env.IV;

  constructor() {
    this.iv = this.iv.slice(16);
  }

  encrypt(text: string): string {
    const cipher = crypto.createCipheriv(
      this.algorithm,
      this.secretKey,
      this.iv
    );
    const encrypted = Buffer.concat([cipher.update(text), cipher.final()]);

    return encrypted.toString("hex");
  }

  decrypt(text: string): string {
    const encryptedText = Buffer.from(text, "hex");
    const decipher = crypto.createDecipheriv(
      this.algorithm,
      this.secretKey,
      this.iv
    );
    const decrypted = Buffer.concat([
      decipher.update(encryptedText),
      decipher.final(),
    ]);

    return decrypted.toString();
  }
}
