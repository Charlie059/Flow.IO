package org.flowio.tenant.exception;

import org.springframework.http.HttpStatus;

public class TenantExistException extends BaseException {
    public TenantExistException(String message) {
        super(HttpStatus.FORBIDDEN, 2001, message);
    }
}
