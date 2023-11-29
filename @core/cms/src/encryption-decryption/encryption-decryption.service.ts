// src/encryption-decryption/encryption-decryption.service.ts
import { Injectable, Logger } from "@nestjs/common";
import NodeVault, * as vault from "node-vault";

@Injectable()
export class EncryptionDecryptionService {
  private vaultClient: NodeVault.client;

  constructor() {
    try {
      this.vaultClient = vault({
        apiVersion: "v1",
        endpoint: process.env.VAULT_ADDR,
        token: process.env.VAULT_TOKEN,
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
  async encryptData(data: any): Promise<string> {
    try {
      const base64data = Buffer.from(data).toString("base64");
      const result = await this.vaultClient.write(
        process.env.VAULT_TRANSIT_ENCRYPT_PATH,
        {
          plaintext: base64data,
        }
      );

      return result.data.ciphertext;
    } catch (error: any) {
      Logger.error(error);
      throw error;
    }
  }

  /**
   * Decrypt data using Vault Transit engine
   * @param cipherText
   * @returns decrypted data
   */
  async decryptData(cipherText: string): Promise<string> {
    try {
      const result = await this.vaultClient.write(
        `${process.env.VAULT_TRANSIT_DECRYPT_PATH}`,
        { ciphertext: cipherText }
      );
      return Buffer.from(result.data.plaintext, "base64").toString();
    } catch (error) {
      Logger.error(error);
      throw error;
    }
  }

  /**
   * Store encrypted data in Vault KV store
   * @param nodeID - unique identifier for the node
   * @param encryptedData - encrypted credentials
   */
  async storeData(credentialId: string, encryptedData: string): Promise<void> {
    try {
      const path = `${process.env.VAULT_KV_PATH}/data/${credentialId}`;
      await this.vaultClient.write(path, {
        data: {
          encrypted_value: encryptedData,
        },
      });
    } catch (error) {
      Logger.error(error);
      throw error;
    }
  }

  /**
   * Retrieve encrypted data from Vault KV store
   * @param path
   */
  async retrieveData(credentialId: string): Promise<string> {
    try {
      const path = `${process.env.VAULT_KV_PATH}/data/${credentialId}`;
      const result = await this.vaultClient.read(path);
      return result.data.data.encrypted_value;
    } catch (error) {
      Logger.error(error);
      throw error;
    }
  }
}
