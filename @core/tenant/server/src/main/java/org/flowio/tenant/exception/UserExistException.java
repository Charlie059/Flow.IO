package org.flowio.tenant.exception;

import org.flowio.tenant.error.ResponseError;

public class UserExistException extends BaseException {
    public UserExistException() {
        super(ResponseError.USER_ALREADY_EXISTS);
    }
}
