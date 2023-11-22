/* eslint-disable @typescript-eslint/no-explicit-any */
// CredentialFactory.ts
import { ICredential } from "../../common/interfaces/ICredential";
import { OAuth2Credential } from "./OAuth2Credential";

export class CredentialFactory {
  static createCredential(type: string, data: any): ICredential {
    switch (type) {
      case "OAuth2":
        return new OAuth2Credential(data);
      default:
        throw new Error(`Unsupported credential type: ${type}`);
    }
  }
}
