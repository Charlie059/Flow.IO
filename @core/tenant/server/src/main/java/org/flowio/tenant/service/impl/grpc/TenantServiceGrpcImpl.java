package org.flowio.tenant.service.impl.grpc;

import com.google.rpc.Code;
import com.google.rpc.Status;
import io.grpc.protobuf.StatusProto;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.flowio.tenant.entity.BusinessType;
import org.flowio.tenant.entity.Tenant;
import org.flowio.tenant.error.ResponseErrors;
import org.flowio.tenant.exception.BusinessTypeNotFoundException;
import org.flowio.tenant.proto.TenantCreateRequest;
import org.flowio.tenant.proto.TenantCreateResponse;
import org.flowio.tenant.proto.TenantServiceGrpc;
import org.flowio.tenant.service.BusinessTypeService;
import org.flowio.tenant.service.TenantService;

@GrpcService
@AllArgsConstructor
public class TenantServiceGrpcImpl extends TenantServiceGrpc.TenantServiceImplBase {
    private final TenantService tenantService;
    private final BusinessTypeService businessTypeService;

    @Override
    public void create(TenantCreateRequest request, StreamObserver<TenantCreateResponse> responseObserver) {
        BusinessType businessType = businessTypeService.getById(request.getBusinessTypeId());
        if (businessType == null) {
            Status status = Status.newBuilder().setCode(Code.NOT_FOUND.getNumber())
                .setMessage(ResponseErrors.BUSINESS_TYPE_NOT_FOUND.getMessage())
                .build();
            responseObserver.onError(StatusProto.toStatusRuntimeException(status));
            return;
        }

        Tenant existingTenant = tenantService.getByName(request.getName());
        if (existingTenant != null) {
            Status status = Status.newBuilder().setCode(Code.ALREADY_EXISTS.getNumber())
                .setMessage(ResponseErrors.TENANT_NAME_ALREADY_EXISTS.getMessage())
                .build();
            responseObserver.onError(StatusProto.toStatusRuntimeException(status));
            return;
        }

        try {
            org.flowio.tenant.dto.request.TenantCreateRequest internalRequest = new org.flowio.tenant.dto.request.TenantCreateRequest(
                request.getName(), request.getAdminEmail(), request.getBusinessTypeId()
            );
            Tenant tenant = tenantService.create(internalRequest);

            TenantCreateResponse response = TenantCreateResponse.newBuilder()
                .setId(tenant.getId())
                .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (BusinessTypeNotFoundException ex) {
            Status status = Status.newBuilder().setCode(Code.NOT_FOUND.getNumber())
                .setMessage(ResponseErrors.BUSINESS_TYPE_NOT_FOUND.getMessage())
                .build();
            responseObserver.onError(StatusProto.toStatusRuntimeException(status));
        }
    }
}
