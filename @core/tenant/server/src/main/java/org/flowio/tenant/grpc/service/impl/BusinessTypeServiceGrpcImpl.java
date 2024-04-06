package org.flowio.tenant.grpc.service.impl;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.flowio.tenant.proto.BusinessTypeListResponse;
import org.flowio.tenant.proto.BusinessTypeProto;
import org.flowio.tenant.proto.BusinessTypeServiceGrpc;
import org.flowio.tenant.service.BusinessTypeService;

@GrpcService
@RequiredArgsConstructor
public class BusinessTypeServiceGrpcImpl extends BusinessTypeServiceGrpc.BusinessTypeServiceImplBase {
    private final BusinessTypeService businessTypeService;

    @Override
    public void list(Empty request, StreamObserver<BusinessTypeListResponse> responseObserver) {
        var businessTypes = businessTypeService.list().stream()
            .map(businessType -> BusinessTypeProto.newBuilder()
                .setId(businessType.getId())
                .setName(businessType.getName())
                .build()
            )
            .toList();

        var response = BusinessTypeListResponse.newBuilder()
            .addAllBusinessTypes(businessTypes)
            .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
