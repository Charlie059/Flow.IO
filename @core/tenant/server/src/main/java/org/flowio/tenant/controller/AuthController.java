package org.flowio.tenant.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.flowio.tenant.dto.request.TokenValidateRequest;
import org.flowio.tenant.dto.response.TokenValidateResponse;
import org.flowio.tenant.entity.Response;
import org.flowio.tenant.exception.InvalidTokenException;
import org.flowio.tenant.service.AccessTokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
class AuthController {
    private final AccessTokenService accessTokenService;

    /**
     * Validate an access token.
     *
     * @param request {@link TokenValidateRequest}
     * @return {@link Response} of token validation.
     */
    @PostMapping("/validate")
    ResponseEntity<Response<TokenValidateResponse>> validateAccessToken(@Valid @RequestBody TokenValidateRequest request) {
        var token = accessTokenService.getByToken(request.getToken());
        if (token == null) {
            throw new InvalidTokenException();
        }

        if (!accessTokenService.isTokenValid(token)) {
            throw new InvalidTokenException();
        }

        return ResponseEntity.ok(Response.success(
            TokenValidateResponse.builder().userId(token.getUserId()).build()
        ));
    }
}
