package org.flowio.tenant.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.flowio.tenant.dto.request.UserCreateRequest;
import org.flowio.tenant.dto.request.UserLoginRequest;
import org.flowio.tenant.dto.response.UserCreateResponse;
import org.flowio.tenant.dto.response.UserLoginResponse;
import org.flowio.tenant.entity.RefreshToken;
import org.flowio.tenant.entity.Response;
import org.flowio.tenant.entity.AccessToken;
import org.flowio.tenant.entity.User;
import org.flowio.tenant.service.RefreshTokenService;
import org.flowio.tenant.service.AccessTokenService;
import org.flowio.tenant.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
class UserController {
    private final UserService userService;
    private final AccessTokenService accessTokenService;
    private final RefreshTokenService refreshTokenService;

    /**
     * Create a new user.
     *
     * @param request {@link UserCreateRequest}
     * @return {@link Response} of the created user id.
     */
    @PostMapping("")
    ResponseEntity<Response<UserCreateResponse>> createUser(@Valid @RequestBody UserCreateRequest request) {
        User user = userService.create(request);

        return new ResponseEntity<>(
            Response.success(UserCreateResponse.builder().id(user.getId()).build()),
            HttpStatus.CREATED
        );
    }

    @PostMapping("/login")
    ResponseEntity<Response<UserLoginResponse>> login(@Valid @RequestBody UserLoginRequest request) {
        // get logged in User
        User user = userService.login(request);

        // generate token
        AccessToken accessToken = accessTokenService.createToken(user);

        // generate refresh token
        RefreshToken refreshToken = refreshTokenService.createToken(user);

        return ResponseEntity.ok(Response.success(
            UserLoginResponse.builder()
                .id(user.getId())
                .tenantId(user.getTenantId())
                .accessToken(accessToken.toDto())
                .refreshToken(refreshToken.toDto())
                .build()
        ));
    }
}
