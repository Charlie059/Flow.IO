package org.flowio.tenant.dto.response;

import lombok.Builder;

@Builder
public record TenantCreateResponse(
    Long tenantId
) {
}
