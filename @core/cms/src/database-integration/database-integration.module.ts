// src/database-integration/database-integration.module.ts
import { Module } from "@nestjs/common";
import { TypeOrmModule } from "@nestjs/typeorm";
import { DatabaseIntegrationService } from "./database-integration.service";
import { Credential } from "./entities/credential.entity";

@Module({
  imports: [
    TypeOrmModule.forRoot({
      type: "postgres",
      host: "db",
      port: 5432,
      username: process.env.DB_USERNAME,
      password: process.env.DB_PASSWORD,
      database: process.env.DB_DATABASE,
      entities: [Credential],
      synchronize: true, // TODO: disable this in production
    }),
    TypeOrmModule.forFeature([Credential]),
  ],
  providers: [DatabaseIntegrationService],
  exports: [DatabaseIntegrationService],
})
export class DatabaseIntegrationModule {}
