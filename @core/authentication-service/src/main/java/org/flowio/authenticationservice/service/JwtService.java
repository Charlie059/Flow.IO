package org.flowio.authenticationservice.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.flowio.authenticationservice.model.LoginUser;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970"; // todo use environment variable

    // TODO read from yml
    private final long accessTokenExpirationTime = 1000 * 60 * 10; // 10 mins
    private final long refreshTokenExpirationTime = 1000 * 60 * 60 * 24; // 1 day

    private String buildAccessToken(LoginUser loginUser, String familyId) {
        return buildAccessToken(loginUser.getLoginEmail(), familyId);
    }

    String buildAccessToken(String loginEmail, String familyId) {
        return generateJwtToken(
                new HashMap<>(){{
                    put("tokenType", "accessToken");
                    put("familyId", familyId);
                    put("myIat", String.valueOf(System.currentTimeMillis()));
                }},
                accessTokenExpirationTime,
                loginEmail
        );
    }

    String buildRefreshToken(String loginEmail, String familyId) {
        return generateJwtToken(
                new HashMap<>() {{
                    put("tokenType", "refreshToken");
                    put("familyId", familyId);
                    put("myIat", String.valueOf(System.currentTimeMillis()));
                }},
                refreshTokenExpirationTime,
                loginEmail
        );
    }

    public String extractUserEmail(String jwtToken) {
        return extractClaim(jwtToken, Claims::getSubject);
    }

    public <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(jwtToken);
        return claimsResolver.apply(claims);
    }

    private String generateJwtToken(
            Map<String, Object> extraClaims,
            long expirationIn,
            String username
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(username)
//                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationIn)) // an hour
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String jwtToken) {
        return ! isTokenExpired(jwtToken);
    }

    private boolean isTokenExpired(String jwtToken) {
        return extractExpiration(jwtToken).before(new Date());
    }


    public String extractTokenType(String jwtToken) {
        var tokenType = extractClaim(jwtToken, claims -> claims.get("tokenType"));
        return tokenType.toString();
    }

    public Long extractFamilyId(String jwtToken) {
        var iat = extractClaim(jwtToken, claims -> claims.get("familyId"));
        if (iat != null) {
            return Long.parseLong(iat.toString());
        }
        return null;
    }

    public Long extractMyIat(String jwtToken) {
        var iat = extractClaim(jwtToken, claims -> claims.get("myIat"));
        if (iat != null) {
            return Long.parseLong(iat.toString());
        }
        return null;
    }

    private Date extractExpiration(String jwtToken) {
        return extractClaim(jwtToken, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }


}
