package org.flowio.tenant.exception;

import org.springframework.http.HttpStatus;

public class InvalidCredentialsException extends BaseException {
    public InvalidCredentialsException() {
        super(HttpStatus.UNAUTHORIZED, 401, "Invalid credentials.");
    }
}
