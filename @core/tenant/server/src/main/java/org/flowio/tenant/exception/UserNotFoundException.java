package org.flowio.tenant.exception;

import org.flowio.tenant.error.ResponseError;

public class UserNotFoundException extends BaseException {
    public UserNotFoundException() {
        super(ResponseError.USER_NOT_FOUND);
    }
}
