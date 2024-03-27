package org.flowio.tenant.service;

public interface TokenService {
    String generateToken();

    boolean isTokenValid(String token);
}
