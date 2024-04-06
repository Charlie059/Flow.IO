package org.flowio.tenant.grpc.service.impl;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.flowio.tenant.proto.AuthServiceGrpc;
import org.flowio.tenant.proto.ValidateTokenRequest;
import org.flowio.tenant.proto.ValidateTokenResponse;
import org.flowio.tenant.service.AccessTokenService;

@GrpcService
@RequiredArgsConstructor
public class AuthServiceGrpcImpl extends AuthServiceGrpc.AuthServiceImplBase {
    private final AccessTokenService accessTokenService;

    @Override
    public void validateToken(ValidateTokenRequest request, StreamObserver<ValidateTokenResponse> responseObserver) {
        var token = accessTokenService.getByToken(request.getToken());
        var isValid = token != null && accessTokenService.isTokenValid(token);
        var response = ValidateTokenResponse.newBuilder()
            .setIsValid(isValid)
            .setUserId(isValid ? token.getUserId() : 0L)
            .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
