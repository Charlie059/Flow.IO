// src/database-integration/database-integration.service.ts

import { Injectable, Logger } from "@nestjs/common";
import { InjectRepository } from "@nestjs/typeorm";
import { Repository } from "typeorm";
import { Credential } from "./entities/credential.entity";
import { CreateCredentialDto } from "./dto/create-credential.dto";
import { NotFoundException } from "@nestjs/common";

@Injectable()
export class DatabaseIntegrationService {
  constructor(
    @InjectRepository(Credential)
    private credentialRepository: Repository<Credential>
  ) {}

  async createCredential(
    createCredentialDto: CreateCredentialDto
  ): Promise<Credential> {
    Logger.log(
      "Received create_credential db request: ",
      createCredentialDto,
      "DatabaseIntegrationService"
    );
    const newCredential = this.credentialRepository.create(createCredentialDto);
    return await this.credentialRepository.save(newCredential);
  }

  async deleteCredential(id: number): Promise<void> {
    const result = await this.credentialRepository.delete(id);
    if (result.affected === 0) {
      throw new NotFoundException(`Credential with ID "${id}" not found`);
    }
  }
}
