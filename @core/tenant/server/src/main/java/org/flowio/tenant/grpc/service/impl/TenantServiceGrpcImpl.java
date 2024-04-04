package org.flowio.tenant.grpc.service.impl;

import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.flowio.tenant.entity.Tenant;
import org.flowio.tenant.exception.TenantExistException;
import org.flowio.tenant.proto.AdminUserResp;
import org.flowio.tenant.proto.TenantCreateRequest;
import org.flowio.tenant.proto.TenantCreateResponse;
import org.flowio.tenant.proto.TenantGetRequest;
import org.flowio.tenant.proto.TenantGetResponse;
import org.flowio.tenant.proto.TenantServiceGrpc;
import org.flowio.tenant.service.BusinessTypeService;
import org.flowio.tenant.service.RngService;
import org.flowio.tenant.service.TenantService;
import org.flowio.tenant.service.UserService;

@GrpcService
@AllArgsConstructor
public class TenantServiceGrpcImpl extends TenantServiceGrpc.TenantServiceImplBase {
    private final TenantService tenantService;
    private final BusinessTypeService businessTypeService;
    private final RngService rngService;
    private final UserService userService;

    @Override
    public void create(TenantCreateRequest request, StreamObserver<TenantCreateResponse> responseObserver) {
        businessTypeService.getByIdOrThrow(request.getBusinessTypeId());
        Tenant existingTenant = tenantService.getByName(request.getName());
        if (existingTenant != null) {
            throw new TenantExistException();
        }

        org.flowio.tenant.dto.request.TenantCreateRequest internalRequest = new org.flowio.tenant.dto.request.TenantCreateRequest(
            request.getName(), request.getAdminEmail(), request.getBusinessTypeId()
        );
        Tenant tenant = tenantService.create(internalRequest);
        // create admin user
        var adminPassword = rngService.randomPassword(16);
        var adminUser = userService.createAdmin(tenant, request.getAdminEmail(), adminPassword);

        TenantCreateResponse response = TenantCreateResponse.newBuilder()
            .setId(tenant.getId())
            .setAdminUser(AdminUserResp.newBuilder()
                .setId(adminUser.getId())
                .setEmail(adminUser.getEmail())
                .setPassword(adminPassword)
                .build())
            .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void get(TenantGetRequest request, StreamObserver<TenantGetResponse> responseObserver) {
        Tenant tenant = tenantService.getByIdOrThrow(request.getId());

        TenantGetResponse response = TenantGetResponse.newBuilder()
            .setId(tenant.getId())
            .setName(tenant.getName())
            .setBusinessTypeId(tenant.getBusinessTypeId())
            .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
