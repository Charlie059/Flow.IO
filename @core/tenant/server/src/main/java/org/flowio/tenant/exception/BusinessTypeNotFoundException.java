package org.flowio.tenant.exception;

import org.flowio.tenant.error.ResponseError;

public class BusinessTypeNotFoundException extends BaseException {
    public BusinessTypeNotFoundException() {
        super(ResponseError.BUSINESS_TYPE_NOT_FOUND);
    }
}
