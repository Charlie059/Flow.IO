package org.flowio.tenant.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.flowio.tenant.entity.Response;

/**
 * This is basically an enum class that covers all the possible errors that can be returned by the API
 */
@Getter
@RequiredArgsConstructor
public enum ResponseErrors {
    TENANT_NOT_FOUND(1000, "Tenant not found"),
    TENANT_NAME_ALREADY_EXISTS(1001, "Tenant name already exists"),
    USER_NOT_FOUND(2000, "User not found"),
    USER_ALREADY_EXISTS(2001, "User already exists"),
    BUSINESS_TYPE_NOT_FOUND(3000, "Business type not found"),
    BUSINESS_TYPE_ALREADY_EXISTS(3001, "Business type already exists");

    private final int code;
    private final String message;

    public Response toResponse() {
        return Response.error(code, message);
    }
}
