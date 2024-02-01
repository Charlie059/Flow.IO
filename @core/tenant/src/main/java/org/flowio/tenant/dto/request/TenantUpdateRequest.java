package org.flowio.tenant.dto.request;

public record TenantUpdateRequest(
    String tenantName,
    String businessType
) {
}
