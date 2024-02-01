package org.flowui.tenant.dto.request;

public record TenantUpdateRequest(
    String tenantName,
    String businessType
) {
}
