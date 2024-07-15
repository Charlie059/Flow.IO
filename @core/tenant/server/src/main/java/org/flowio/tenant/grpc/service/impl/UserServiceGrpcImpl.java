package org.flowio.tenant.grpc.service.impl;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.flowio.tenant.entity.AccessToken;
import org.flowio.tenant.entity.RefreshToken;
import org.flowio.tenant.entity.User;
import org.flowio.tenant.entity.enums.Role;
import org.flowio.tenant.exception.InvalidArgumentException;
import org.flowio.tenant.exception.UnauthenticatedException;
import org.flowio.tenant.proto.AssignRoleRequest;
import org.flowio.tenant.proto.AssignRoleResponse;
import org.flowio.tenant.proto.UserCreateRequest;
import org.flowio.tenant.proto.UserCreateResponse;
import org.flowio.tenant.proto.UserLoginRequest;
import org.flowio.tenant.proto.UserLoginResponse;
import org.flowio.tenant.proto.UserServiceGrpc;
import org.flowio.tenant.service.AccessTokenService;
import org.flowio.tenant.service.RefreshTokenService;
import org.flowio.tenant.service.UserService;
import org.flowio.tenant.utils.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@GrpcService
@RequiredArgsConstructor
public class UserServiceGrpcImpl extends UserServiceGrpc.UserServiceImplBase {
    private final UserService userService;
    private final AccessTokenService accessTokenService;
    private final RefreshTokenService refreshTokenService;

    @Override
    public void create(UserCreateRequest request, StreamObserver<UserCreateResponse> responseObserver) {
        var internalRequest = org.flowio.tenant.dto.request.UserCreateRequest.builder()
            .email(request.getEmail())
            .password(request.getPassword())
            .tenantId(request.getTenantId())
            .build();
        var user = userService.create(internalRequest, Role.USER);

        UserCreateResponse response = UserCreateResponse.newBuilder()
            .setId(user.getId())
            .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void login(UserLoginRequest request, StreamObserver<UserLoginResponse> responseObserver) {
        var internalRequest = org.flowio.tenant.dto.request.UserLoginRequest.builder()
            .email(request.getEmail())
            .password(request.getPassword())
            .tenantId(request.getTenantId())
            .build();
        User user = userService.login(internalRequest);

        // generate token
        AccessToken accessToken = accessTokenService.createToken(user);

        // generate refresh token
        RefreshToken refreshToken = refreshTokenService.createToken(user);

        UserLoginResponse response = UserLoginResponse.newBuilder()
            .setId(user.getId())
            .setTenantId(user.getTenantId())
            .setAccessToken(accessToken.toProto())
            .setRefreshToken(refreshToken.toProto())
            .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    @PreAuthorize("hasAnyAuthority('tenant:manage_users')")
    public void assignRole(AssignRoleRequest request, StreamObserver<AssignRoleResponse> responseObserver) {
        final User user = userService.getByIdOrThrow(request.getUserId());
        final User authUser = SecurityUtils.getCurrentUser();

        // check whether the user is authenticated and belongs to the same tenant
        if (authUser == null || !authUser.getTenantId().equals(user.getTenantId())) {
            throw new UnauthenticatedException();
        }

        List<Role> roles;
        try {
            roles = request.getRolesList().stream()
                .map(Role::valueOf)
                .toList();
        } catch (IllegalArgumentException ex) {
            throw new InvalidArgumentException("Invalid roles");
        }

        userService.assignRoles(user, roles);

        var response = AssignRoleResponse.newBuilder()
            .setUserId(user.getId())
            .addAllRoles(request.getRolesList())
            .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
