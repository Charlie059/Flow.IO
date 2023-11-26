// src/database-integration/database-integration.service.spec.ts

import { Test, TestingModule } from '@nestjs/testing';
import { DatabaseIntegrationService } from './database-integration.service';
import { getRepositoryToken } from '@nestjs/typeorm';
import { Credential } from './entities/credential.entity';
import { Repository } from 'typeorm';

describe('DatabaseIntegrationService', () => {
  let service: DatabaseIntegrationService;
  let credentialRepository: Repository<Credential>;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [
        DatabaseIntegrationService,
        {
          provide: getRepositoryToken(Credential),
          useValue: {
            create: jest.fn(),
            save: jest.fn(),
          },
        },
      ],
    }).compile();

    service = module.get<DatabaseIntegrationService>(
      DatabaseIntegrationService,
    );
    credentialRepository = module.get<Repository<Credential>>(
      getRepositoryToken(Credential),
    );
  });

  it('should create a credential', async () => {
    const createCredentialDto = {
      userId: 1,
      serviceNameId: 1,
      credentialTypeId: 1,
      data: {},
    };
    const credential = new Credential();
    Object.assign(credential, createCredentialDto);

    jest.spyOn(credentialRepository, 'create').mockReturnValue(credential);
    jest.spyOn(credentialRepository, 'save').mockResolvedValue(credential);

    expect(await service.createCredential(createCredentialDto)).toEqual(
      credential,
    );
    expect(credentialRepository.create).toHaveBeenCalledWith(
      createCredentialDto,
    );
    expect(credentialRepository.save).toHaveBeenCalledWith(credential);
  });
});
