package org.flowio.tenant.grpc.service.impl;

import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.flowio.tenant.proto.UserCreateRequest;
import org.flowio.tenant.proto.UserCreateResponse;
import org.flowio.tenant.proto.UserServiceGrpc;
import org.flowio.tenant.service.TenantService;
import org.flowio.tenant.service.UserService;

@GrpcService
@AllArgsConstructor
public class UserServiceGrpcImpl extends UserServiceGrpc.UserServiceImplBase {
    private final TenantService tenantService;
    private final UserService userService;

    @Override
    public void create(UserCreateRequest request, StreamObserver<UserCreateResponse> responseObserver) {
        // TODO: update this impl
//        Tenant tenant = tenantService.getById(request.getTenantId());
//        if (tenant == null) {
//            Status status = Status.newBuilder().setCode(Code.NOT_FOUND.getNumber())
//                .setMessage(ResponseErrors.TENANT_NOT_FOUND.getMessage())
//                .build();
//            responseObserver.onError(StatusProto.toStatusRuntimeException(status));
//            return;
//        }
//
//        User existingUser = userService.getByEmailAndTenant(request.getEmail(), tenant);
//        if (existingUser != null) {
//            Status status = Status.newBuilder().setCode(Code.ALREADY_EXISTS.getNumber())
//                .setMessage(ResponseErrors.USER_ALREADY_EXISTS.getMessage())
//                .build();
//            responseObserver.onError(StatusProto.toStatusRuntimeException(status));
//            return;
//        }
//
//        User user = userService.create(new org.flowio.tenant.dto.request.UserCreateRequest(
//            request.getEmail(), request.getPassword(), request.getTenantId()
//        ));
//        UserCreateResponse response = UserCreateResponse.newBuilder()
//            .setId(user.getId())
//            .build();
//
//        responseObserver.onNext(response);
//        responseObserver.onCompleted();
    }
}
