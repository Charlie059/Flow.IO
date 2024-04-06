package org.flowio.tenant.grpc.service.impl;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.flowio.tenant.entity.enums.Role;
import org.flowio.tenant.proto.ListRolesResponse;
import org.flowio.tenant.proto.RoleServiceGrpc;

import java.util.stream.Stream;

@GrpcService
@RequiredArgsConstructor
public class RoleServiceGrpcImpl extends RoleServiceGrpc.RoleServiceImplBase {
    @Override
    public void list(Empty request, StreamObserver<ListRolesResponse> responseObserver) {
        var roles = Stream.of(Role.values())
            .map(Enum::name)
            .toList();

        var response = ListRolesResponse.newBuilder()
            .addAllRoles(roles)
            .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
