package org.flowio.tenant.exception;

import org.springframework.http.HttpStatus;

public class UnauthenticatedException extends BaseException {
    public UnauthenticatedException() {
        super(HttpStatus.UNAUTHORIZED, 401, "You are not authenticated to access this resource.");
    }
}
