package org.flowio.tenant.exception;

import org.flowio.tenant.error.ResponseError;
import org.springframework.http.HttpStatus;

public class TenantExistException extends BaseException {
    public TenantExistException() {
        super(HttpStatus.FORBIDDEN, ResponseError.TENANT_ALREADY_EXISTS);
    }

    public TenantExistException(Long tenantId) {
        super(HttpStatus.FORBIDDEN, ResponseError.TENANT_ALREADY_EXISTS, new ResponseData(tenantId));
    }

    private record ResponseData(Long tenantId) {
    }
}
