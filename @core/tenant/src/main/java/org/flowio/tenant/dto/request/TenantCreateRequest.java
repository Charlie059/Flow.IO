package org.flowio.tenant.dto.request;

public record TenantCreateRequest(
    String tenantName,
    String adminEmail,
    int businessTypeId
) {
}
