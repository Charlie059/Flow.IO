package org.flowio.tenant.exception;

import org.flowio.tenant.error.ResponseError;

public class UnauthenticatedException extends BaseException {
    public UnauthenticatedException() {
        super(ResponseError.UNAUTHORIZED);
    }
}
