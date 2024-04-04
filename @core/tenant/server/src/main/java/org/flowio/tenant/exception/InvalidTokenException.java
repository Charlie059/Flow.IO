package org.flowio.tenant.exception;

import org.flowio.tenant.error.ResponseError;

public class InvalidTokenException extends BaseException {
    public InvalidTokenException() {
        super(ResponseError.INVALID_TOKEN);
    }
}
