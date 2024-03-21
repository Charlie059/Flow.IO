package org.flowio.tenant.service;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

/**
 * This service is responsible for generating and validating JWTs.
 * For token storage and management, see {@link TokenService}.
 *
 * @see TokenService
 */
public interface JwtService {
    /**
     * Generate a JWT for the user
     *
     * @param user The user to generate the token for
     * @return The generated token
     */
    String generateToken(UserDetails user);

    String generateToken(UserDetails user, Map<String, Object> claims);

    /**
     * Generate a refresh token for the user
     *
     * @param user The user to generate the token for
     * @return The generated token
     */
    String generateRefreshToken(UserDetails user);

    String generateRefreshToken(UserDetails user, Map<String, Object> claims);

    /**
     * Extract the username from the token
     *
     * @param token The token to extract the username from
     * @return The username extracted from the token
     */
    String extractUsername(String token);

    Long extractCreatedAt(String token);

    Long extractExpiresAt(String token);

    boolean isTokenValid(String token, UserDetails user);

}
