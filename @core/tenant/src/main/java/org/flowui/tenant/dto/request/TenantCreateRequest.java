package org.flowui.tenant.dto.request;

public record TenantCreateRequest(
    String tenantName,
    String adminEmail,
    String businessType
) {
}
