package org.flowio.tenant.controller;

import org.flowio.tenant.entity.enums.Role;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.MOCK,
    properties = "grpc.server.port=0" // must set to 0 for random port to avoid port already in use
)
@AutoConfigureMockMvc
class RoleControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    void testGet() throws Exception {
        var roles = Arrays.stream(Role.values()).map(Enum::name).toArray(String[]::new);

        mvc.perform(get("/api/v1/roles"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.code").value(0))
            .andExpect(jsonPath("$.data").isArray())
            .andExpect(jsonPath("$.data").value(Matchers.contains(roles)));
    }
}
