package org.flowio.tenant.exception;

import org.springframework.http.HttpStatus;

public class BusinessTypeNotFoundException extends BaseException {
    public BusinessTypeNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, 3000, message);
    }
}
