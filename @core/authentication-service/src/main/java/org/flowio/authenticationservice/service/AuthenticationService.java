package org.flowio.authenticationservice.service;


import lombok.RequiredArgsConstructor;
import org.flowio.authenticationservice.dto.AuthenticationRequest;
import org.flowio.authenticationservice.dto.AuthenticationResponse;
import org.flowio.authenticationservice.dto.RegisterRequest;
import org.flowio.authenticationservice.model.LoginUser;
import org.flowio.authenticationservice.repository.LoginUserRepository;
import org.flowio.authenticationservice.model.Role;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final LoginUserRepository loginUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    private final RedisService redisService;

    private final long accessTokenExpirationTime = 1000 * 60 * 10; // 10 mins
    private final long refreshTokenExpirationTime = 1000 * 60 * 60 * 24; // 1 day



    private String buildAccessToken(LoginUser loginUser) {
        return buildAccessToken(loginUser.getLoginEmail());
    }

    private String buildAccessToken(String loginEmail) {
            return jwtService.generateJwtToken(
            new HashMap<>(){{ put("tokenType", "accessToken"); }},
            accessTokenExpirationTime,
            loginEmail
            );
    }

    private String buildRefreshToken(LoginUser loginUser, String firstIat) {
        return buildRefreshToken(loginUser.getLoginEmail(), firstIat);
    }

    private String buildRefreshToken(String loginEmail, String firstIat) {
            return jwtService.generateJwtToken(
            new HashMap<>(){{
                put("tokenType", "refreshToken");
                put("firstIat", firstIat);
                put("thisIat", System.currentTimeMillis());
            }},
            refreshTokenExpirationTime,
            loginEmail
            );
    }

    private void addRTokenLastUsage(String key){
        redisService.addKeyValue(
                key,
                System.currentTimeMillis(),
                refreshTokenExpirationTime
        );
    }

    private Long getRTokenLastUsage(String key){
        return redisService.getValue(key);
    }

    public AuthenticationResponse register(RegisterRequest request) {

        if(Stream.of(
                request.getFirstname(),
                request.getLastname(),
                request.getLoginEmail(),
                request.getPassword()
                )
            .anyMatch(str -> str == null || str.isEmpty())) {
            return AuthenticationResponse.builder()
                .message("Firstname, lastname, login email and password must not be null or empty.")
                .build();
        }

        var loginUser = LoginUser.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .loginEmail(request.getLoginEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        loginUserRepository.save(loginUser);
        var accessToken = buildAccessToken(loginUser);
        var refreshToken = buildRefreshToken(loginUser, String.valueOf(System.currentTimeMillis()));
                return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .message("Successfully registered.")
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getLoginEmail(),
                        request.getPassword()
                )
        );
        var loginUser = loginUserRepository.findByLoginEmail(request.getLoginEmail())
                        .orElseThrow();
        var accessToken = buildAccessToken(loginUser);
        var refreshToken = buildRefreshToken(loginUser, String.valueOf(System.currentTimeMillis()));
                return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .message("Successfully authenticated.")
                .build();
    }

    public AuthenticationResponse getAccessTokenUsingRefreshToken(AuthenticationRequest request){

        final var refreshToken = request.getRefreshToken();

        final String userEmail = jwtService.extractUserEmail(refreshToken);
        final Long firstIat = jwtService.extractFirstIat(refreshToken);
        final Long thisIat = jwtService.extractThisIat(refreshToken);
        final String tokenType = jwtService.extractTokenType(refreshToken);


        if (
                !  "refreshToken".equals(tokenType)
                        || firstIat == null
                        || thisIat == null
                        || userEmail == null
                        || ! jwtService.isTokenValid(refreshToken)
        ) {
            return AuthenticationResponse.builder()
                    .message("Invalid refresh token.")
                    .build();
        }

        // check if issuedAt before lastUsageDate (invalid / exceeded the usage limit / revoked)

        final Long lastUsage = getRTokenLastUsage(userEmail + firstIat);

        System.out.println("firstIat: " + firstIat );
        System.out.println("lastUsage: " + lastUsage );
        System.out.println("thisIat: " + thisIat );

        if (lastUsage != null && lastUsage > thisIat){
            // add last usage to redis
            addRTokenLastUsage(userEmail + firstIat);

            return AuthenticationResponse.builder()
                    .message("The refresh token has exceeded the usage limit.")
                    .build();
        }


        // refresh last usage
        addRTokenLastUsage(userEmail + firstIat);


        var newRefreshToken = buildRefreshToken(userEmail, String.valueOf(firstIat));
        var accessToken = buildAccessToken(userEmail);


        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(newRefreshToken)
                .message("Succeed.")
                .build();

    }

    public AuthenticationResponse revokeRefreshToken(AuthenticationRequest request) {
        final var refreshToken = request.getRefreshToken();

        final String userEmail = jwtService.extractUserEmail(refreshToken);
        final Long firstIat = jwtService.extractFirstIat(refreshToken);
        final String tokenType = jwtService.extractTokenType(refreshToken);

        if (
                !  "refreshToken".equals(tokenType)
                        || firstIat == null
                        || userEmail == null
                        || ! jwtService.isTokenValid(refreshToken)
        ) {
            return AuthenticationResponse.builder()
                    .message("Invalid refresh token.")
                    .build();
        }

        // add last usage to redis
        addRTokenLastUsage(userEmail + firstIat);

        return AuthenticationResponse.builder()
                .message("Revoked.")
                .build();
    }
}
