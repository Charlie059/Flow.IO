package org.flowio.tenant.exception;

import org.flowio.tenant.error.ResponseError;
import org.springframework.http.HttpStatus;

public class TenantNotFoundException extends BaseException {
    public TenantNotFoundException() {
        super(HttpStatus.NOT_FOUND, ResponseError.TENANT_NOT_FOUND);
    }
}
