package org.flowio.tenant.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenantCreateRequest {
    @NotNull(message = "name is required")
    private String name;
    @NotNull(message = "adminEmail is required")
    private String adminEmail;
    @NotNull(message = "businessTypeId is required")
    @Min(value = 1, message = "businessTypeId is invalid")
    private Integer businessTypeId;
}
