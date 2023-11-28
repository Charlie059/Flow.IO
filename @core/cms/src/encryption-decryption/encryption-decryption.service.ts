// src/encryption-decryption/encryption-decryption.service.ts
import { Injectable, Logger } from "@nestjs/common";
import * as vault from "node-vault";

@Injectable()
export class EncryptionDecryptionService {
  private vaultClient: any;

  constructor() {
    try {
      this.vaultClient = vault({
        apiVersion: "v1",
        endpoint: "http://host.docker.internal:8200",
        token: "myroot",
      });
    } catch (error) {
      Logger.error(error);
      throw error;
    }
  }

  /**
   * Encrypt data using Vault Transit engine
   * @param credentialId
   * @param data
   * @returns encrypted data
   */
  async encryptData(credentialId: string, data: any): Promise<string> {
    try {
      const base64data = Buffer.from(data).toString("base64");
      Logger.log(base64data, "EncryptionDecryptionService");
      const result = await this.vaultClient.write("transit/encrypt/key", {
        plaintext: base64data,
      });

      Logger.log(result.data.ciphertext, "EncryptionDecryptionService");
      return result.data.ciphertext;
    } catch (error: any) {
      Logger.error(error);
      throw error;
    }
  }

  /**
   * Decrypt data using Vault Transit engine
   * @param key
   * @param ciphertext
   * @returns decrypted data
   */
  async decryptData(key, ciphertext): Promise<string> {
    try {
      const result = await this.vaultClient.write(
        `${process.env.VAULT_TRANSIT_PATH}/decrypt/${key}`,
        { ciphertext }
      );
      return Buffer.from(result.data.plaintext, "base64").toString();
    } catch (error) {
      Logger.error(error);
      throw error;
    }
  }

  /**
   * Store encrypted data in Vault KV store
   * @param path
   * @param encryptedData
   */
  async storeData(path: string, encryptedData: any): Promise<void> {
    try {
      await this.vaultClient.write(path, { encrypted_value: encryptedData });
    } catch (error) {
      Logger.error(error);
      throw error;
    }
  }

  /**
   * Retrieve encrypted data from Vault KV store
   * @param path
   */
  async retrieveData(path: string): Promise<string> {
    try {
      const result = await this.vaultClient.read(path);
      return result.data.encrypted_value;
    } catch (error) {
      Logger.error(error);
      throw error;
    }
  }

  /**
   * Rotate key in Vault Transit engine
   * @param keyName
   */
  async rotateKey(keyName: string): Promise<void> {
    try {
      await this.vaultClient.write(
        `${process.env.VAULT_TRANSIT_PATH}/keys/${keyName}/rotate`
      );
    } catch (error) {
      Logger.error(error);
      throw error;
    }
  }
}
