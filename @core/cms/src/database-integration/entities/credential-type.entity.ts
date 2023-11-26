// src/database-integration/entities/credential-type.entity.ts

import { Entity, PrimaryGeneratedColumn, Column } from 'typeorm';

@Entity()
export class CredentialType {
  @PrimaryGeneratedColumn()
  id: number;

  @Column({ unique: true })
  type: string;
}
