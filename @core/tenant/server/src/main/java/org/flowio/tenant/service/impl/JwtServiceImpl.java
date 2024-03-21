package org.flowio.tenant.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.flowio.tenant.config.JwtConfig;
import org.flowio.tenant.exception.InvalidJwtException;
import org.flowio.tenant.service.JwtService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {
    private final JwtConfig jwtConfig;

    @Override
    public String generateToken(UserDetails user) {
        return generateToken(user, Map.of());
    }

    @Override
    public String generateToken(UserDetails user, Map<String, Object> claims) {
        return generateToken(user, claims, jwtConfig.getExpiration());
    }

    @Override
    public String generateRefreshToken(UserDetails user) {
        return generateToken(user, Map.of());
    }

    @Override
    public String generateRefreshToken(UserDetails user, Map<String, Object> claims) {
        return generateToken(user, claims, jwtConfig.getRefreshExpiration());
    }

    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public Long extractCreatedAt(String token) {
        return extractClaim(token, claims -> claims.getIssuedAt().getTime());
    }

    @Override
    public Long extractExpiresAt(String token) {
        return extractClaim(token, claims -> claims.getExpiration().getTime());
    }

    @Override
    public boolean isTokenValid(String token, UserDetails user) {
        final String username = extractUsername(token);
        return (username.equals(user.getUsername())) && !isTokenExpired(token);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts
                .parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        } catch (IllegalArgumentException | JwtException ex) {
            throw new InvalidJwtException();
        }
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private String generateToken(UserDetails user, Map<String, Object> claims, long expiration) {
        return Jwts.builder()
            .claims(claims)
            .subject(user.getUsername())
            .issuedAt(new Date(System.currentTimeMillis()))
            .notBefore(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + expiration))
            .signWith(getKey())
            .compact();
    }

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes());
    }
}
