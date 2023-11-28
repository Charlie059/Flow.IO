// src/database-integration/entities/credential.entity.ts

import { Entity, PrimaryGeneratedColumn, Column } from "typeorm";

@Entity()
export class Credential {
  @PrimaryGeneratedColumn()
  id: number;

  @Column()
  userId: number;

  @Column()
  serviceName: string;

  @Column("integer", { array: true })
  nodeAccessIds: number[];

  @Column()
  credentialType: string;

  @Column()
  encryptedCredential: string;

  @Column({ default: () => "CURRENT_TIMESTAMP" })
  createdAt: Date;

  @Column({ default: () => "CURRENT_TIMESTAMP" })
  updatedAt: Date;
}
