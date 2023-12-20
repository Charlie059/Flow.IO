import { Module } from '@nestjs/common';
import { ExternalAuthIntegrationService } from './external-auth-integration.service';

@Module({
  providers: [ExternalAuthIntegrationService],
  exports: [ExternalAuthIntegrationService],
})
export class ExternalAuthIntegrationModule {}
