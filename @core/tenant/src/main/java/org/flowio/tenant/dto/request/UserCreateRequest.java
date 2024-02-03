package org.flowio.tenant.dto.request;

public record UserCreateRequest(
    String email,
    String password,
    long tenantId
) {
}
