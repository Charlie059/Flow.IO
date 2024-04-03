package org.flowio.tenant.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TenantUpdateRequest {
    @NotNull(message = "Name is required")
    private String name;

    @NotNull(message = "Business type is required")
    @Min(value = 1, message = "Business type is required")
    private Integer businessTypeId;
}
