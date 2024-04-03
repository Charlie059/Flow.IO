package org.flowio.tenant.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * This is basically an enum class that covers all the possible errors that can be returned by the API
 */
@Getter
@RequiredArgsConstructor
public enum ResponseError {
    // standard http responses
    BAD_REQUEST(400, "Bad request"),
    UNAUTHORIZED(401, "Unauthorized"),
    ROUTE_NOT_FOUND(404, "Route not found"),
    INTERNAL_SERVER_ERROR(500, "Internal server error"),
    // custom errors
    TENANT_NOT_FOUND(1000, "Tenant not found"),
    TENANT_ALREADY_EXISTS(1001, "Tenant already exists"),
    USER_NOT_FOUND(2000, "User not found"),
    USER_ALREADY_EXISTS(2001, "User already exists"),
    INVALID_TOKEN(2002, "Invalid token"),
    BUSINESS_TYPE_NOT_FOUND(3000, "Business type not found"),
    BUSINESS_TYPE_ALREADY_EXISTS(3001, "Business type already exists");

    private final int code;
    private final String message;
}
