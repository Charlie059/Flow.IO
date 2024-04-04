package org.flowio.tenant.grpc.config;

import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.security.authentication.BearerAuthenticationReader;
import net.devh.boot.grpc.server.security.authentication.GrpcAuthenticationReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class GrpcConfig {
    @Bean
    public GrpcAuthenticationReader grpcAuthenticationReader() {
        return new BearerAuthenticationReader(accessToken -> {
            log.info("access token: {}", accessToken);
            return null;
        });
    }
}
