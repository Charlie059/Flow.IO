package org.flowio.tenant.grpc.service.impl;

import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.flowio.tenant.entity.AccessToken;
import org.flowio.tenant.entity.RefreshToken;
import org.flowio.tenant.entity.User;
import org.flowio.tenant.entity.enums.Role;
import org.flowio.tenant.proto.UserCreateRequest;
import org.flowio.tenant.proto.UserCreateResponse;
import org.flowio.tenant.proto.UserLoginRequest;
import org.flowio.tenant.proto.UserLoginResponse;
import org.flowio.tenant.proto.UserServiceGrpc;
import org.flowio.tenant.service.AccessTokenService;
import org.flowio.tenant.service.RefreshTokenService;
import org.flowio.tenant.service.UserService;

@GrpcService
@AllArgsConstructor
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
}
