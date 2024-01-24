import { NestFactory } from "@nestjs/core";
import { MicroserviceOptions, Transport } from "@nestjs/microservices";
import { AppModule } from "./app.module";
import { readFileSync } from "fs";

async function bootstrap() {
  const httpsOptions = {
    key: readFileSync("./src/cert/server.key"),
    cert: readFileSync("./src/cert/server.cert"),
  };
  // Create NestJS application
  const app = await NestFactory.create(AppModule, { httpsOptions });

  // Create microservice options object
  const microservice = app.connectMicroservice<MicroserviceOptions>({
    transport: Transport.REDIS,
    options: {
      host: process.env.REDIS_HOST,
      port: Number(process.env.REDIS_PORT),
    },
  });

  await microservice.listen();
  await app.listen(3000, '0.0.0.0');
}

bootstrap();
