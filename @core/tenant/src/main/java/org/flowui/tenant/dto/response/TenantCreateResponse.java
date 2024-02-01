package org.flowui.tenant.dto.response;

import lombok.Builder;

@Builder
public record TenantCreateResponse(
    Long tenantId,
    String message
) {
}
