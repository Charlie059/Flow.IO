package org.flowio.tenant.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.flowio.tenant.dto.request.UserAssignRoleRequest;
import org.flowio.tenant.dto.request.UserCreateRequest;
import org.flowio.tenant.dto.request.UserLoginRequest;
import org.flowio.tenant.dto.response.UserAssignRoleResponse;
import org.flowio.tenant.dto.response.UserCreateResponse;
import org.flowio.tenant.dto.response.UserLoginResponse;
import org.flowio.tenant.entity.AccessToken;
import org.flowio.tenant.entity.RefreshToken;
import org.flowio.tenant.entity.Response;
import org.flowio.tenant.entity.User;
import org.flowio.tenant.entity.enums.Role;
import org.flowio.tenant.exception.InvalidArgumentException;
import org.flowio.tenant.exception.UnauthenticatedException;
import org.flowio.tenant.service.AccessTokenService;
import org.flowio.tenant.service.RefreshTokenService;
import org.flowio.tenant.service.UserService;
import org.flowio.tenant.utils.SecurityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
        final User user = userService.create(request, Role.USER);

        return new ResponseEntity<>(
            Response.success(UserCreateResponse.builder().id(user.getId()).build()),
            HttpStatus.CREATED
        );
    }

    @PostMapping("/login")
    ResponseEntity<Response<UserLoginResponse>> login(@Valid @RequestBody UserLoginRequest request) {
        // get logged in User
        final User user = userService.login(request);

        // generate token
        final AccessToken accessToken = accessTokenService.createToken(user);

        // generate refresh token
        final RefreshToken refreshToken = refreshTokenService.createToken(user);

        return ResponseEntity.ok(Response.success(
            UserLoginResponse.builder()
                .id(user.getId())
                .tenantId(user.getTenantId())
                .accessToken(accessToken.toDto())
                .refreshToken(refreshToken.toDto())
                .build()
        ));
    }

    @PostMapping("/{userId}/roles")
    @PreAuthorize("hasAnyAuthority('tenant:manage_users')")
    ResponseEntity<Response<UserAssignRoleResponse>> assignRole(@PathVariable Long userId, @Valid @RequestBody UserAssignRoleRequest request) {
        final User user = userService.getByIdOrThrow(userId);
        final User authUser = SecurityUtils.getCurrentUser();

        // check whether the user is authenticated and belongs to the same tenant
        if (authUser == null || !authUser.getTenantId().equals(user.getTenantId())) {
            throw new UnauthenticatedException();
        }

        List<Role> roles;
        try {
            roles = request.getRoles().stream()
                .map(Role::valueOf)
                .toList();
        } catch (IllegalArgumentException ex) {
            throw new InvalidArgumentException("Invalid roles");
        }

        userService.assignRoles(user, roles);
        return ResponseEntity.ok(Response.success(
            UserAssignRoleResponse.builder()
                .id(user.getId())
                .roles(roles)
                .build()
        ));
    }
}
