package org.flowio.tenant.exception;

import org.flowio.tenant.error.ResponseError;

import java.util.Map;

public class TenantExistException extends BaseException {
    public TenantExistException() {
        super(ResponseError.TENANT_ALREADY_EXISTS);
    }

    public TenantExistException(Long tenantId) {
        super(ResponseError.TENANT_ALREADY_EXISTS, Map.of("tenantId", String.valueOf(tenantId)));
    }
}
