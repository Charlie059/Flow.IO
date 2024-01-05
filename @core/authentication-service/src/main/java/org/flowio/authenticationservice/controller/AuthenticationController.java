package org.flowio.authenticationservice.controller;

import lombok.RequiredArgsConstructor;
import org.flowio.authenticationservice.dto.AuthenticationRequest;
import org.flowio.authenticationservice.dto.AuthenticationResponse;
import org.flowio.authenticationservice.service.AuthenticationService;
import org.flowio.authenticationservice.dto.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/get-access-token")
    public ResponseEntity<AuthenticationResponse> getAccessToken(
            @RequestBody AuthenticationRequest request
    ) throws Exception {
        return ResponseEntity.ok(service.getAccessTokenUsingRefreshToken(request));
    }

    @PostMapping("/revoke-refresh-token")
    public ResponseEntity<AuthenticationResponse> revokeRefreshToken(
            @RequestBody AuthenticationRequest request
    ) throws Exception {
        return ResponseEntity.ok(service.revokeRefreshToken(request));
    }
}
