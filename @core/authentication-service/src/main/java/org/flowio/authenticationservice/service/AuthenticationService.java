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

import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final LoginUserRepository loginUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RedisService redisService;

    // TODO read from yml
    private final long accessTokenExpirationTime = 1000 * 60 * 10; // 10 mins
    private final long refreshTokenExpirationTime = 1000 * 60 * 60 * 24; // 1 day


    private AuthenticationResponse buildTokenResponse(String userEmail, String familyId) {
        var newRefreshToken = jwtService.buildRefreshToken(userEmail, familyId);
        var accessToken = jwtService.buildAccessToken(userEmail, familyId);
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(newRefreshToken)
                .message("Succeed.")
                .build();
    }



    public AuthenticationResponse register(RegisterRequest request) {

        if(Stream.of(
                request.getFirstname(),
                request.getLastname(),
                request.getLoginEmail(),
                request.getPassword()
                )
            .anyMatch(str -> str == null || str.length() <= 2)) {
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
        return buildTokenResponse(loginUser.getLoginEmail(), String.valueOf(System.currentTimeMillis()));
    }

    /**
     * Authenticate user and return access token and refresh token.
     * @param request login email and password
     * @return access token and refresh token
     */
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getLoginEmail(),
                            request.getPassword()
                    )
            );
            var loginUser = loginUserRepository.findByLoginEmail(request.getLoginEmail())
                        .orElseThrow();
                return buildTokenResponse(loginUser.getLoginEmail(), String.valueOf(System.currentTimeMillis()));
        } catch (Exception e) {
            return AuthenticationResponse.builder()
                    .message("Invalid login email or password.")
                    .build();
        }
    }

    /**
     * Get access token using refresh token.
     * @param request AuthenticationRequest
     * @return access token and refresh token
     * @throws Exception
     */
    public AuthenticationResponse getAccessTokenUsingRefreshToken(AuthenticationRequest request) throws Exception {

        final var refreshToken = request.getRefreshToken();

        final String userEmail = jwtService.extractUserEmail(refreshToken);
        final Long familyId = jwtService.extractFamilyId(refreshToken);
        final String familyIdStr = String.valueOf(familyId);
        final Long myIat = jwtService.extractMyIat(refreshToken);
        final String tokenType = jwtService.extractTokenType(refreshToken);


        if (
                !  "refreshToken".equals(tokenType)
                        || familyId == null
                        || myIat == null
                        || userEmail == null
                        || ! jwtService.isTokenValid(refreshToken)
        ) {
            return AuthenticationResponse.builder()
                    .message("Invalid refresh token.")
                    .build();
        }

        // check if issuedAt before lastUsageDate (invalid / exceeded the usage limit / revoked)


        final Long lastUsage = redisService.getAndUpdateTokenLastUsage(userEmail, familyIdStr, 'R');

        if (lastUsage != null && lastUsage > myIat){
            return AuthenticationResponse.builder()
                    .message("The refresh token has exceeded the usage limit.")
                    .build();
        }


        return buildTokenResponse(userEmail, familyIdStr);
    }

    public AuthenticationResponse revokeRefreshToken(AuthenticationRequest request) {
        final var refreshToken = request.getRefreshToken();

        final String userEmail = jwtService.extractUserEmail(refreshToken);
        final Long familyId = jwtService.extractFamilyId(refreshToken);
        final String familyIdStr = String.valueOf(familyId);
        final String tokenType = jwtService.extractTokenType(refreshToken);

        if (
                !  "refreshToken".equals(tokenType)
                        || familyId == null
                        || userEmail == null
                        || ! jwtService.isTokenValid(refreshToken)
        ) {
            return AuthenticationResponse.builder()
                    .message("Invalid refresh token.")
                    .build();
        }

        // add last usage to redis
//        redisService.getAndUpdateTokenLastUsage(userEmail, familyIdStr, 'R');
        redisService.addTokenLastUsage(userEmail, familyIdStr, 'R');

        return AuthenticationResponse.builder()
                .message("Revoked.")
                .build();
    }
}
