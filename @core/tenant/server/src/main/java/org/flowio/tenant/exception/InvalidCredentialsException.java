package org.flowio.tenant.exception;

import org.flowio.tenant.error.ResponseError;

public class InvalidCredentialsException extends BaseException {
    public InvalidCredentialsException() {
        super(ResponseError.INVALID_CREDENTIALS);
    }
}
