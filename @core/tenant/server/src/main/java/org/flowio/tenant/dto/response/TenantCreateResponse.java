package org.flowio.tenant.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TenantCreateResponse {
    private Long id;
    private TenantAdminUser adminUser;


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TenantAdminUser {
        private Long id;
        private String email;
        private String password;
    }
}
