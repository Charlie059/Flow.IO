package org.flowio.tenant.exception;

import org.flowio.tenant.error.ResponseError;
import org.springframework.http.HttpStatus;

public class UserExistException extends BaseException {
    public UserExistException() {
        super(HttpStatus.FORBIDDEN, ResponseError.USER_ALREADY_EXISTS);
    }
}
