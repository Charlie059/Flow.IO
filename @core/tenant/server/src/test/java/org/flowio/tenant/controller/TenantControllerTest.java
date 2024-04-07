package org.flowio.tenant.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.flowio.tenant.dto.request.TenantCreateRequest;
import org.flowio.tenant.entity.Tenant;
import org.flowio.tenant.service.TenantService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@DirtiesContext
class TenantControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private TenantService tenantService;

    @Test
    void testCreate() throws Exception {
        TenantCreateRequest request = TenantCreateRequest.builder()
            .name("test")
            .adminEmail("example@test.com")
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
        // create a mock tenant
        TenantCreateRequest request = TenantCreateRequest.builder()
            .name("testGet")
            .adminEmail("example@test.com")
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
}
