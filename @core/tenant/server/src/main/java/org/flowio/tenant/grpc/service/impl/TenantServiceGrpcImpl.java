package org.flowio.tenant.grpc.service.impl;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.flowio.tenant.entity.Tenant;
import org.flowio.tenant.exception.UnauthenticatedException;
import org.flowio.tenant.proto.AdminUserProto;
import org.flowio.tenant.proto.TenantCreateRequest;
import org.flowio.tenant.proto.TenantCreateResponse;
import org.flowio.tenant.proto.TenantGetRequest;
import org.flowio.tenant.proto.TenantListUsersRequest;
import org.flowio.tenant.proto.TenantListUsersResponse;
import org.flowio.tenant.proto.TenantProto;
import org.flowio.tenant.proto.TenantServiceGrpc;
import org.flowio.tenant.proto.TenantUpdateRequest;
import org.flowio.tenant.proto.UserProto;
import org.flowio.tenant.service.RngService;
import org.flowio.tenant.service.TenantService;
import org.flowio.tenant.service.UserService;
import org.flowio.tenant.utils.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;

@GrpcService
@RequiredArgsConstructor
public class TenantServiceGrpcImpl extends TenantServiceGrpc.TenantServiceImplBase {
    private final TenantService tenantService;
    private final RngService rngService;
    private final UserService userService;

    @Override
    public void create(TenantCreateRequest request, StreamObserver<TenantCreateResponse> responseObserver) {
        var internalRequest = new org.flowio.tenant.dto.request.TenantCreateRequest(
            request.getName(), request.getAdminEmail(), request.getBusinessTypeId()
        );
        Tenant tenant = tenantService.create(internalRequest);
        // create admin user
        var adminPassword = rngService.randomPassword(16);
        var adminUser = userService.createAdmin(tenant, request.getAdminEmail(), adminPassword);

        TenantCreateResponse response = TenantCreateResponse.newBuilder()
            .setId(tenant.getId())
            .setAdminUser(AdminUserProto.newBuilder()
                .setId(adminUser.getId())
                .setEmail(adminUser.getEmail())
                .setPassword(adminPassword)
                .build())
            .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void get(TenantGetRequest request, StreamObserver<TenantProto> responseObserver) {
        Tenant tenant = tenantService.getByIdOrThrow(request.getId());

        TenantProto response = TenantProto.newBuilder()
            .setId(tenant.getId())
            .setName(tenant.getName())
            .setBusinessTypeId(tenant.getBusinessTypeId())
            .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    @PreAuthorize("hasAnyAuthority('tenant:update')")
    public void update(TenantUpdateRequest request, StreamObserver<TenantProto> responseObserver) {
        Tenant tenant = tenantService.getByIdOrThrow(request.getId());

        // make sure the user has access to the tenant
        var user = SecurityUtils.getCurrentUser();
        if (user == null || !user.getTenantId().equals(tenant.getId())) {
            throw new UnauthenticatedException();
        }

        var internalRequest = new org.flowio.tenant.dto.request.TenantUpdateRequest(
            request.getName(), request.getBusinessTypeId()
        );

        tenantService.update(tenant, internalRequest);

        TenantProto response = TenantProto.newBuilder()
            .setId(tenant.getId())
            .setName(tenant.getName())
            .setBusinessTypeId(tenant.getBusinessTypeId())
            .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    @PreAuthorize("hasAnyAuthority('tenant:list_users')")
    public void listUsers(TenantListUsersRequest request, StreamObserver<TenantListUsersResponse> responseObserver) {
        Tenant tenant = tenantService.getByIdOrThrow(request.getId());

        var users = userService.getByTenant(tenant).stream()
            .map(user -> UserProto.newBuilder()
                .setId(user.getId())
                .setEmail(user.getEmail())
                .build()
            )
            .toList();

        TenantListUsersResponse response = TenantListUsersResponse.newBuilder()
            .addAllUsers(users)
            .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
