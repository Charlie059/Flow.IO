package org.flowio.tenant.exception;

import org.flowio.tenant.error.ResponseError;

public class TenantNotFoundException extends BaseException {
    public TenantNotFoundException() {
        super(ResponseError.TENANT_NOT_FOUND);
    }
}
