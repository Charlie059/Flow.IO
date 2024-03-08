package org.flowio.tenant.exception;

import org.flowio.tenant.error.ResponseError;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends BaseException {
    public UserNotFoundException() {
        super(HttpStatus.NOT_FOUND, ResponseError.USER_NOT_FOUND);
    }
}
