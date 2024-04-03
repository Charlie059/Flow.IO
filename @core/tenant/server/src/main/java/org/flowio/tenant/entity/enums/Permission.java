package org.flowio.tenant.entity.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permission {
    TENANT_UPDATE("tenant:update"),
    TENANT_DELETE("tenant:delete");

    private final String permission;
}
