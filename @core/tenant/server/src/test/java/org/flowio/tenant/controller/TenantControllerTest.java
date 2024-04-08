package org.flowio.tenant.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.flowio.tenant.dto.request.TenantCreateRequest;
import org.flowio.tenant.dto.request.TenantUpdateRequest;
import org.flowio.tenant.entity.AccessToken;
import org.flowio.tenant.entity.Tenant;
import org.flowio.tenant.entity.User;
import org.flowio.tenant.service.AccessTokenService;
import org.flowio.tenant.service.TenantService;
import org.flowio.tenant.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.MOCK,
    properties = "grpc.server.port=0" // must set to 0 for random port to avoid port already in use
)
@AutoConfigureMockMvc
class TenantControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private TenantService tenantService;
    @Autowired
    private UserService userService;
    @Autowired
    private AccessTokenService accessTokenService;

    @Test
    void testCreate() throws Exception {
        TenantCreateRequest request = TenantCreateRequest.builder()
            .name("testCreate")
            .adminEmail("test1@example.com")
            .businessTypeId(1)
            .build();

        mvc.perform(
                MockMvcRequestBuilders.post("/api/v1/tenants")
                    .content(mapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(0))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data").isMap());
    }

    @Test
    void testGet() throws Exception {
        // mock a tenant
        TenantCreateRequest request = TenantCreateRequest.builder()
            .name("testGet")
            .adminEmail("test2@example.com")
            .businessTypeId(1)
            .build();
        Tenant tenant = tenantService.create(request);

        mvc.perform(
                MockMvcRequestBuilders.get("/api/v1/tenants/{tenantId}", tenant.getId())
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(0))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data").isMap())
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(tenant.getId()));
    }

    @Test
    void testUpdate() throws Exception {
        // mock a tenant
        TenantCreateRequest createRequest = TenantCreateRequest.builder()
            .name("testUpdate")
            .adminEmail("test3@example.com")
            .businessTypeId(1)
            .build();
        Tenant tenant = tenantService.create(createRequest);

        // mock a user
        User user = userService.createAdmin(tenant, "example@test.com", "password");

        // mock a token
        AccessToken token = accessTokenService.createToken(user);

        // TEST #1: full request
        TenantUpdateRequest request1 = TenantUpdateRequest.builder()
            .name("testUpdate2")
            .businessTypeId(4)
            .build();
        mvc.perform(
                MockMvcRequestBuilders.patch("/api/v1/tenants/{tenantId}", tenant.getId())
                    .header("Authorization", "Bearer " + token.getToken())
                    .content(mapper.writeValueAsString(request1))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(0))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data").isMap())
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.name").value("testUpdate2"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.businessTypeId").value(4));

        // TEST #2: partial request
        TenantUpdateRequest request2 = TenantUpdateRequest.builder()
            .name("testUpdate3")
            .build();
        mvc.perform(
                MockMvcRequestBuilders.patch("/api/v1/tenants/{tenantId}", tenant.getId())
                    .header("Authorization", "Bearer " + token.getToken())
                    .content(mapper.writeValueAsString(request2))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(0))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data").isMap())
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.name").value("testUpdate3"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.businessTypeId").value(4));
    }
}
