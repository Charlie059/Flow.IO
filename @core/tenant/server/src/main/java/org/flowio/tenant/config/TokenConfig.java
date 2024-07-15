package org.flowio.tenant.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "application.token")
@Data
public class TokenConfig {
    private long accessTokenExpiresIn;
    private long refreshTokenExpiresIn;
}
