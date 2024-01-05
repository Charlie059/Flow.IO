package org.flowio.authenticationservice.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Configuration
@ConfigurationProperties(prefix = "jwt")
@RequiredArgsConstructor
@Getter
public class JwtConfig {

    @Value("${jwt.secretKey}")
    private String secretKey;
}