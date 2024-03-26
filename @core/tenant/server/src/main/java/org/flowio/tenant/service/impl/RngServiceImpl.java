package org.flowio.tenant.service.impl;

import lombok.RequiredArgsConstructor;
import org.flowio.tenant.service.RngService;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class RngServiceImpl implements RngService {
    private static final String PASSWORD_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()_+-=[]{}|;':\",./<>?";

    private final SecureRandom secureRandom;

    @Override
    public String randomPassword(int length) {
        StringBuilder result = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            result.append(PASSWORD_CHARS.charAt(secureRandom.nextInt(PASSWORD_CHARS.length())));
        }

        return result.toString();
    }
}
