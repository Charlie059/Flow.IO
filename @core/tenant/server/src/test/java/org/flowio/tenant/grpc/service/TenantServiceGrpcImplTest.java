package org.flowio.tenant.grpc.service;

import io.grpc.internal.testing.StreamRecorder;
import org.flowio.tenant.grpc.service.impl.TenantServiceGrpcImpl;
import org.flowio.tenant.proto.TenantCreateRequest;
import org.flowio.tenant.proto.TenantCreateResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@DirtiesContext
class TenantServiceGrpcImplTest {
    private final Logger logger = LoggerFactory.getLogger(TenantServiceGrpcImplTest.class);

    @Autowired
    private TenantServiceGrpcImpl tenantService;

    @Test
    void testCreate() throws Exception {
        TenantCreateRequest request = TenantCreateRequest.newBuilder()
            .setName("Test1")
            .setAdminEmail("admin@test1.com")
            .setBusinessTypeId(1)
            .build();
        StreamRecorder<TenantCreateResponse> responseObserver = StreamRecorder.create();
        tenantService.create(request, responseObserver);
        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
            Assertions.fail("The call did not terminate in time");
        }
        Assertions.assertNull(responseObserver.getError());
        List<TenantCreateResponse> results = responseObserver.getValues();
        Assertions.assertEquals(1, results.size());
        TenantCreateResponse response = results.get(0);
        logger.info(response.toString());
        Assertions.assertTrue(response.getId() > 0);
        Assertions.assertEquals("admin@test1.com", response.getAdminUser().getEmail());
    }
}
