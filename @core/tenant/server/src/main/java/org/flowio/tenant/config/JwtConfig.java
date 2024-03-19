package org.flowio.tenant.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "application.security.jwt")
@Data
public class JwtConfig {
    private String secret;
    private Long expiration;
    private Long refreshExpiration;
}
