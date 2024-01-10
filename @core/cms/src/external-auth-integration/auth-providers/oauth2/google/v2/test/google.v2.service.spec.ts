/* eslint-disable @typescript-eslint/no-unused-vars */
import { HttpService } from "@nestjs/axios";
import { GoogleV2OAuth2Service } from "../google.v2.service";
import { Test, TestingModule } from "@nestjs/testing";
import { mockEncryptionDecryptionService, mockHttpService } from "./mocks";
import { EncryptionDecryptionService } from "~/encryption-decryption/encryption-decryption.service";
import { GoogleV2OAuth2Config } from "../google.v2.config";
import * as dotenv from "dotenv";

dotenv.config({ path: `.env` });

describe("GoogleV2OAuth2Service", () => {
  let service: GoogleV2OAuth2Service;
  let httpService: HttpService;
  let encryptionDecryptionService: EncryptionDecryptionService;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [
        GoogleV2OAuth2Service,
        {
          provide: HttpService,
          useValue: mockHttpService,
        },
        {
          provide: EncryptionDecryptionService,
          useValue: mockEncryptionDecryptionService,
        },
        {
          provide: "GoogleV2OAuth2Config",
          useFactory: () => new GoogleV2OAuth2Config().oAuth2Config,
        },
      ],
    }).compile();

    service = module.get<GoogleV2OAuth2Service>(GoogleV2OAuth2Service);
    httpService = module.get<HttpService>(HttpService);
    encryptionDecryptionService = module.get<EncryptionDecryptionService>(EncryptionDecryptionService);
  });

  /**
   * Test the service to get the OAuth2 Redirect URL
   */
  describe("authenticate", () => {
    it("should return a valid OAuth URL", async () => {
      const url = await service.authenticate();
      expect(typeof url).toBe("string");
      expect(url).toContain("https://accounts.google.com/o/oauth2/v2/auth");
      expect(url).toContain("client_id=");
      expect(url).toContain("redirect_uri=");
      expect(url).toContain("response_type=");
      expect(url).toContain("scope=");
      expect(url).toContain("state=");
      expect(url).toContain("access_type=");
    });
  });

  //TODO: Add more tests

  describe("handleCallback", () => {
    it("should return a valid token response", async () => {
      const query = {
        code: "1234",
      };
      const res = {
        status: jest.fn().mockReturnThis(),
        json: jest.fn().mockReturnThis(),
      };

      await service.handleCallback(query, res);

      expect(res.status).toHaveBeenCalledWith(200);

      expect(res.json).toHaveBeenCalledWith(
        expect.objectContaining({
          access_token: "mock_access_token",
          refresh_token: "mock_refresh_token",
          expires_in: 3600,
          token_type: "Bearer",
        }),
      );
    });
  });

  describe("validateToken", () => {
    it("should return a valid token verification response", async () => {
      const res = await service.verifyToken("mock_access_token");
      expect(res).toEqual({
        isValid: true,
        expiresIn: 3599,
        scopes: ["https://www.googleapis.com/auth/userinfo.email"],
      });
    });
  });

  describe("refreshToken", () => {
    it("should return a valid token response", async () => {
      const res = await service.refreshToken("mock_refresh_token");
      expect(res).toEqual({
        access_token: "mock_access_token",
        refresh_token: "mock_refresh_token",
        expires_in: 3600,
        token_type: "Bearer",
      });
    });
  });
});
