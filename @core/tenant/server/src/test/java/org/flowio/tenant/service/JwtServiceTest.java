package org.flowio.tenant.service;

import org.flowio.tenant.config.JwtConfig;
import org.flowio.tenant.dto.request.UserCreateRequest;
import org.flowio.tenant.entity.Tenant;
import org.flowio.tenant.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

@SpringBootTest
class JwtServiceTest {
    private final Logger logger = LoggerFactory.getLogger(JwtServiceTest.class);

    @Autowired
    private TenantService tenantService;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private JwtConfig jwtConfig;

    @Test
    void testConfig() {
        // make sure the secret environment variable is set
        Assertions.assertNotEquals("${JWT_SECRET}", jwtConfig.getSecret());

        // make sure the expiration time is set
        Assertions.assertTrue(jwtConfig.getExpiration() > 0);
        Assertions.assertTrue(jwtConfig.getRefreshExpiration() > 0);
    }

    @Test
    void testGenerateToken() {
        final User user = User.builder()
            .email("example@domain.com")
            .password("114.514")
            .build();

        final String jwt = jwtService.generateToken(user);
        Assertions.assertNotNull(jwt);
        logger.info("generated token:" + jwt);
        checkToken(user, jwt, jwtConfig.getExpiration());
    }

    @Test
    void testGenerateRefreshToken() {
        final User user = User.builder()
            .email("example2@domain.com")
            .password("1919.810")
            .build();

        final String jwt = jwtService.generateRefreshToken(user);
        Assertions.assertNotNull(jwt);
        logger.info("generated refresh token:" + jwt);
        checkToken(user, jwt, jwtConfig.getRefreshExpiration());
    }

    void checkToken(UserDetails user, String jwt, long expiration) {
        final long now = System.currentTimeMillis();
        final long expires = now + expiration;

        Assertions.assertEquals(user.getUsername(), jwtService.extractUsername(jwt));
        Assertions.assertTrue(jwtService.isTokenValid(jwt, user));

        Long createdAt = jwtService.extractCreatedAt(jwt);
        logger.info("expected:" + now + ", actual:" + createdAt);
        Assertions.assertTrue(Math.abs(now - createdAt) < 2000);

        Long expiresAt = jwtService.extractExpiresAt(jwt);
        logger.info("expected:" + expires + ", actual:" + expiresAt);
        Assertions.assertTrue(Math.abs(expires - expiresAt) < 2000);
    }
}
