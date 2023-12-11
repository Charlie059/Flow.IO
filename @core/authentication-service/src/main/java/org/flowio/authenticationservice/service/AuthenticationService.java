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

import java.util.HashMap;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final LoginUserRepository loginUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    private final int accessTokenExpirationTime = 1000 * 15; // 15 mins
    private final int refreshTokenExpirationTime = 1000 * 60 * 24; // 1 day

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
        var accessToken = jwtService.generateJwtToken(
                        new HashMap<>(){{ put("tokenType", "accessToken"); }},
                        accessTokenExpirationTime,
                        loginUser
                );
        var refreshToken = jwtService.generateJwtToken(
                        new HashMap<>(){{ put("tokenType", "refreshToken"); }},
                        refreshTokenExpirationTime,
                        loginUser
                );
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
        var accessToken = jwtService.generateJwtToken(
                        new HashMap<>(){{ put("tokenType", "accessToken"); }},
                        accessTokenExpirationTime,
                        loginUser
                );
        var refreshToken = jwtService.generateJwtToken(
                        new HashMap<>(){{ put("tokenType", "refreshToken"); }},
                        refreshTokenExpirationTime,
                        loginUser
                );
                return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .message("Successfully authenticated.")
                .build();
    }

    public AuthenticationResponse getAccessTokenUsingRefreshToken(AuthenticationRequest request) {
        var refreshToken = request.getRefreshToken();
        if (! jwtService.isTokenValid(refreshToken) || !  "refreshToken".equals(jwtService.extractTokenType(refreshToken))) {
                return AuthenticationResponse.builder()
                    .message("Invalid refresh token.")
                    .build();
        }
        var accessToken = jwtService.generateJwtToken(
                        new HashMap<>(){{ put("tokenType", "accessToken"); }},
                        accessTokenExpirationTime,
                        jwtService.extractUserEmail(refreshToken)
                );
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .message("Access token successfully generated.")
                .build();

    }

    public AuthenticationResponse renewRefreshToken(AuthenticationRequest request) {
        var refreshToken = request.getRefreshToken();
        if (! jwtService.isTokenValid(refreshToken) || !  "refreshToken".equals(jwtService.extractTokenType(refreshToken))) {
            return AuthenticationResponse.builder()
                    .message("Invalid refresh token.")
                    .build();
        }
        revokeRefreshToken(request);

        var newRefreshToken = jwtService.generateJwtToken(
                new HashMap<>(){{ put("tokenType", "refreshToken"); }},
                refreshTokenExpirationTime,
                jwtService.extractUserEmail(refreshToken)
        );
        return AuthenticationResponse.builder()
                .refreshToken(newRefreshToken)
                .message("Refresh token successfully renewed.")
                .build();

    }

    public AuthenticationResponse revokeRefreshToken(AuthenticationRequest request) {// TODO
        return null;
    }
}
