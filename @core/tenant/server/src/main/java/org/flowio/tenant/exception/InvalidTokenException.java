package org.flowio.tenant.exception;

import org.flowio.tenant.error.ResponseError;
import org.springframework.http.HttpStatus;

public class InvalidTokenException extends BaseException {
    public InvalidTokenException() {
        super(HttpStatus.UNAUTHORIZED, ResponseError.INVALID_TOKEN);
    }
}
