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
    @NotNull(message = "Name is required")
    private String name;

    @NotNull(message = "Admin email is required")
    private String adminEmail;

    @NotNull(message = "Business type is required")
    @Min(value = 1, message = "Business type is required")
    private Integer businessTypeId;
}
