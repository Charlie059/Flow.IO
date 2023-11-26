// src/database-integration/entities/credential.entity.ts

import {
  Entity,
  PrimaryGeneratedColumn,
  Column,
  ManyToOne,
  JoinColumn,
} from 'typeorm';
import { ServiceName } from './service-name.entity';
import { CredentialType } from './credential-type.entity';

@Entity()
export class Credential {
  @PrimaryGeneratedColumn()
  id: number;

  @Column()
  userId: number;

  @ManyToOne(() => ServiceName)
  @JoinColumn({ name: 'service_name_id' })
  serviceName: ServiceName;

  @ManyToOne(() => CredentialType)
  @JoinColumn({ name: 'credential_type_id' })
  credentialType: CredentialType;

  @Column('json')
  data: any;

  @Column({ default: () => 'CURRENT_TIMESTAMP' })
  createdAt: Date;

  @Column({ default: () => 'CURRENT_TIMESTAMP' })
  updatedAt: Date;
}
