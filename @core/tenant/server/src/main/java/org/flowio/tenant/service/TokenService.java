package org.flowio.tenant.service;

public interface TokenService {
    /**
     * Generate a new token
     * @return generated token
     */
    String generateToken();

/**
     * Check if the token is valid
     * @param token The token to check
     * @return Whether the token is valid
     */
    boolean isTokenValid(String token);
}
