package org.flowio.tenant.exception;

import org.springframework.http.HttpStatus;

public class UserExistException extends BaseException {
    public UserExistException(String message) {
        super(HttpStatus.FORBIDDEN, 1001, message);
    }
}
