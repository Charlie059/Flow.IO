package org.flowio.tenant.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenantCreateRequest {
    private String name;
    private String adminEmail;
    private Integer businessTypeId;
}
