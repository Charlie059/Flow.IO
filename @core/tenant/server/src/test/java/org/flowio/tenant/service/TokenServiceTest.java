package org.flowio.tenant.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext
class TokenServiceTest {
    private final Logger logger = LoggerFactory.getLogger(TokenServiceTest.class);

    @Autowired
    private TokenService tokenService;

    @Test
    void testGenerate() {
        String token = tokenService.generateToken();

        Assertions.assertNotNull(token);
        logger.info("generated token:" + token);
        Assertions.assertTrue(tokenService.isTokenValid(token));
    }
}
