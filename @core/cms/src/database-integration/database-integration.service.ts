// src/database-integration/database-integration.service.ts

import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { Credential } from './entities/credential.entity';
import { CreateCredentialDto } from './dto/create-credential.dto';
import { NotFoundException } from '@nestjs/common';
import { ServiceName } from './entities/service-name.entity';
import { CredentialType } from './entities/credential-type.entity';

@Injectable()
export class DatabaseIntegrationService {
  constructor(
    @InjectRepository(Credential)
    private credentialRepository: Repository<Credential>,
    @InjectRepository(ServiceName)
    private serviceNameRepository: Repository<ServiceName>,
    @InjectRepository(CredentialType)
    private credentialTypeRepository: Repository<CredentialType>,
  ) {}

  async createCredential(
    createCredentialDto: CreateCredentialDto,
  ): Promise<Credential> {
    const serviceName = await this.serviceNameRepository.findOne({
      where: {
        id: createCredentialDto.serviceNameId,
      },
    });

    if (!serviceName) {
      throw new NotFoundException(
        `ServiceName with ID "${createCredentialDto.serviceNameId}" not found`,
      );
    }

    const credentialType = await this.credentialTypeRepository.findOne({
      where: {
        id: createCredentialDto.credentialTypeId,
      },
    });

    if (!credentialType) {
      throw new NotFoundException(
        `CredentialType with ID "${createCredentialDto.credentialTypeId}" not found`,
      );
    }

    const newCredential = this.credentialRepository.create({
      userId: createCredentialDto.userId,
      serviceName,
      credentialType,
      data: createCredentialDto.data,
    });

    return await this.credentialRepository.save(newCredential);
  }

  async deleteCredential(id: number): Promise<void> {
    const result = await this.credentialRepository.delete(id);
    if (result.affected === 0) {
      throw new NotFoundException(`Credential with ID "${id}" not found`);
    }
  }
}
