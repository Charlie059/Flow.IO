package org.flowio.tenant.service.impl;

import lombok.RequiredArgsConstructor;
import org.flowio.tenant.service.TokenService;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.zip.CRC32;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int TOKEN_LENGTH = 24;

    private final SecureRandom secureRandom;

    @Override
    public String generateToken() {
        String token = generateToken(TOKEN_LENGTH);
        return token + getChecksum(token);
    }

    private String generateToken(int length) {
        StringBuilder token = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            token.append(CHARS.charAt(secureRandom.nextInt(CHARS.length())));
        }

        return token.toString();
    }

    private String getChecksum(String token) {
        CRC32 crc32 = new CRC32();
        crc32.update(token.getBytes());
        return Long.toHexString(crc32.getValue());
    }

    @Override
    public boolean isTokenValid(String token) {
        if (token == null || token.length() != TOKEN_LENGTH + 8) {
            return false;
        }
        String tokenPart = token.substring(0, token.length() - 8);
        String checksumPart = token.substring(token.length() - 8);
        return getChecksum(tokenPart).equals(checksumPart);
    }
}
