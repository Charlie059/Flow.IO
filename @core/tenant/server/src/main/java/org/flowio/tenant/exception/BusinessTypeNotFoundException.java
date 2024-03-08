package org.flowio.tenant.exception;

import org.flowio.tenant.error.ResponseError;
import org.springframework.http.HttpStatus;

public class BusinessTypeNotFoundException extends BaseException {
    public BusinessTypeNotFoundException() {
        super(HttpStatus.NOT_FOUND, ResponseError.BUSINESS_TYPE_NOT_FOUND);
    }
}
