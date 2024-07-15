package org.flowio.tenant.exception;

import org.flowio.tenant.error.ResponseError;

public class InvalidArgumentException extends BaseException {
    public InvalidArgumentException() {
        super(ResponseError.BAD_REQUEST);
    }

    public InvalidArgumentException(String message) {
        super(ResponseError.BAD_REQUEST, message);
    }
}
