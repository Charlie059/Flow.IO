import { Injectable, Inject, Logger } from "@nestjs/common";
import { HttpService } from "@nestjs/axios";
import { EncryptionDecryptionService } from "~/encryption-decryption/encryption-decryption.service";
import { lastValueFrom } from "rxjs";
import { map } from "rxjs/operators";

import { IBasicAuth } from "../../interface/basic-auth.interface";
import { BasicAuthConfig, BasicAuthVerificationResponse } from "../../@types";

/**
 * Service to handle Mixpanel V1 Basic HTTP Authentication.
 */
@Injectable()
export class MixpanelV1BasicAuthService implements IBasicAuth {
  constructor(
    @Inject("MixpanelV1BasicAuthConfig") private readonly config: BasicAuthConfig,
    private readonly httpService: HttpService,
    private readonly encryptionDecryptionService: EncryptionDecryptionService,
  ) {}

  private readonly logger = new Logger(MixpanelV1BasicAuthService.name);

  async authenticate(username: string, password: string): Promise<object> {
    try {
      const { authenticateUrl } = this.config.provider;
      const data = await lastValueFrom(
        this.httpService.get(authenticateUrl, { auth: { username, password } }).pipe(map((resp) => resp.data)),
      );

      return {
        status: data.status,
        organizationId: Object.keys(data.results?.organizations).at(0),
      };
    } catch (error) {
      this.logger.error(`Authentication failed: ${error}`);
      const data = error.response?.data;
      return data;
    }
  }

  async verify(username: string, password: string): Promise<BasicAuthVerificationResponse> {
    try {
      const authenticationResponse = await this.authenticate(username, password);
      if (!authenticationResponse["organizationId"]) {
        throw new Error("Authentication failed");
      }

      let { verifyUrl } = this.config.provider;
      if (!verifyUrl) {
        throw new Error("verifyUrl not found");
      }

      verifyUrl = verifyUrl.replace("{organizationId}", authenticationResponse["organizationId"]);
      const data = await lastValueFrom(
        this.httpService.get(verifyUrl, { auth: { username, password } }).pipe(map((resp) => resp.data)),
      );

      return {
        isValid: true,
        expiresIn: data.results?.expires,
      };
    } catch (error) {
      this.logger.error(`Service account verification failed: ${error}`);
      return { isValid: false };
    }
  }
}
