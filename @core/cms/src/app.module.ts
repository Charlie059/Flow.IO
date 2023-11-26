import { Module } from '@nestjs/common';
import { ConfigModule } from '@nestjs/config';
import { DatabaseIntegrationModule } from './database-integration/database-integration.module';
import { CredentialManagementModule } from './credential-management/credential-management.module';
import { ExternalAuthIntegrationModule } from './external-auth-integration/external-auth-integration.module';
import { EncryptionDecryptionModule } from './encryption-decryption/encryption-decryption.module';
import { MonitoringModule } from './monitoring/monitoring.module';
import { ApiDocumentationModule } from './api-documentation/api-documentation.module';

@Module({
  imports: [
    ConfigModule.forRoot(),
    DatabaseIntegrationModule,
    CredentialManagementModule,
    ExternalAuthIntegrationModule,
    EncryptionDecryptionModule,
    MonitoringModule,
    ApiDocumentationModule,
  ],
})
export class AppModule {}
