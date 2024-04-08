package org.flowio.tenant.grpc.service;

import io.grpc.internal.testing.StreamRecorder;
import lombok.extern.slf4j.Slf4j;
import org.flowio.tenant.entity.Tenant;
import org.flowio.tenant.grpc.service.impl.TenantServiceGrpcImpl;
import org.flowio.tenant.proto.TenantCreateRequest;
import org.flowio.tenant.proto.TenantCreateResponse;
import org.flowio.tenant.proto.TenantGetRequest;
import org.flowio.tenant.proto.TenantProto;
import org.flowio.tenant.service.TenantService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@Slf4j
class TenantServiceGrpcImplTest {
    @Autowired
    private TenantServiceGrpcImpl tenantServiceGrpc;
    @Autowired
    private TenantService tenantService;

    @Test
    void testCreate() throws Exception {
        TenantCreateRequest request = TenantCreateRequest.newBuilder()
            .setName("testCreateGrpc")
            .setAdminEmail("admin@test1.com")
            .setBusinessTypeId(1)
            .build();
        StreamRecorder<TenantCreateResponse> responseObserver = StreamRecorder.create();
        tenantServiceGrpc.create(request, responseObserver);
        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
            Assertions.fail("The call did not terminate in time");
        }
        Assertions.assertNull(responseObserver.getError());
        List<TenantCreateResponse> results = responseObserver.getValues();
        Assertions.assertEquals(1, results.size());
        TenantCreateResponse response = results.get(0);
        Assertions.assertTrue(response.getId() > 0);
        Assertions.assertEquals("admin@test1.com", response.getAdminUser().getEmail());
    }

    @Test
    void testGet() throws Exception {
        // mock a tenant
        var createRequest = org.flowio.tenant.dto.request.TenantCreateRequest.builder()
            .name("testGetGrpc")
            .businessTypeId(1)
            .build();
        Tenant tenant = tenantService.create(createRequest);

        // TEST
        TenantGetRequest request = TenantGetRequest.newBuilder()
            .setId(tenant.getId())
            .build();
        StreamRecorder<TenantProto> responseObserver = StreamRecorder.create();
        tenantServiceGrpc.get(request, responseObserver);
        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
            Assertions.fail("The call did not terminate in time");
        }
        Assertions.assertNull(responseObserver.getError());
        List<TenantProto> results = responseObserver.getValues();
        Assertions.assertEquals(1, results.size());
        TenantProto response = results.get(0);
        Assertions.assertEquals(tenant.getId(), response.getId());
        Assertions.assertEquals("testGetGrpc", response.getName());
    }
}
