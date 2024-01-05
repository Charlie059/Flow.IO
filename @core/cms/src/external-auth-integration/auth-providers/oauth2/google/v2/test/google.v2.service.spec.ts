import { HttpService } from "@nestjs/axios";
import { GoogleV2OAuth2Service } from "../google.v2.service";
import { Test, TestingModule } from "@nestjs/testing";
import { mockEncryptionDecryptionService, mockHttpService } from "./mocks";
import { EncryptionDecryptionService } from "~/encryption-decryption/encryption-decryption.service";
import { GoogleV2OAuth2Config } from "../google.v2.config";

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
      //TODO: Add more restrictions of string checking
    });
  });

  //TODO: Add more tests
});
