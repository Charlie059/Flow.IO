package org.flowio.authenticationservice.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970"; // todo use environment variable


    public String extractUserEmail(String jwtToken) {
        return extractClaim(jwtToken, Claims::getSubject);
    }

    public <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(jwtToken);
        return claimsResolver.apply(claims);
    }

//    public String generateJwtToken(UserDetails userDetails){
//        return generateJwtToken(new HashMap<>(), userDetails);
//    }

    public String generateJwtToken(
            Map<String, Object> extraClaims,
            int expirationIn,
            UserDetails userDetails
    ) {
        return generateJwtToken(extraClaims, expirationIn, userDetails.getUsername());
    }

    public String generateJwtToken(
            Map<String, Object> extraClaims,
            int expirationIn,
            String username
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationIn)) // an hour
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String jwtToken, UserDetails userDetails) {
        final String username = extractUserEmail(jwtToken);
        return (username.equals((userDetails.getUsername())) && ! isTokenExpired(jwtToken));
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
