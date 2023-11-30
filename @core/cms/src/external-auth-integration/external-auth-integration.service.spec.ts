import { Test, TestingModule } from "@nestjs/testing";
import { ExternalAuthIntegrationService } from "./services/external-auth-integration.service";

describe("ExternalAuthIntegrationService", () => {
  let service: ExternalAuthIntegrationService;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [ExternalAuthIntegrationService],
    }).compile();

    service = module.get<ExternalAuthIntegrationService>(
      ExternalAuthIntegrationService
    );
  });

  it("should be defined", () => {
    expect(service).toBeDefined();
  });
});
