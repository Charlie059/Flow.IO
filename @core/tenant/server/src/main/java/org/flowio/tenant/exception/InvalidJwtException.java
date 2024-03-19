package org.flowio.tenant.exception;

import org.flowio.tenant.error.ResponseError;
import org.springframework.http.HttpStatus;

public class InvalidJwtException extends BaseException {
    public InvalidJwtException() {
        super(HttpStatus.UNAUTHORIZED, ResponseError.INVALID_TOKEN);
    }
}
