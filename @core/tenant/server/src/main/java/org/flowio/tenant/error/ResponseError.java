package org.flowio.tenant.error;

import io.grpc.Status.Code;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * This is basically an enum class that covers all the possible errors that can be returned by the API
 */
@Getter
@RequiredArgsConstructor
public enum ResponseError {
    // standard http responses
    BAD_REQUEST(HttpStatus.BAD_REQUEST, 400, Code.INVALID_ARGUMENT, "Bad request"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, 401, Code.UNAUTHENTICATED, "Unauthorized"),
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, 401, Code.UNAUTHENTICATED, "Invalid credentials"),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, 403, Code.PERMISSION_DENIED, "Access denied"),
    ROUTE_NOT_FOUND(HttpStatus.NOT_FOUND, 404, Code.NOT_FOUND, "Route not found"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500, Code.INTERNAL, "Internal server error"),
    // custom errors
    TENANT_NOT_FOUND(HttpStatus.NOT_FOUND, 1000, Code.INVALID_ARGUMENT, "Tenant not found"),
    TENANT_ALREADY_EXISTS(HttpStatus.FORBIDDEN, 1001, Code.ALREADY_EXISTS, "Tenant already exists"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, 2000, Code.INVALID_ARGUMENT, "User not found"),
    USER_ALREADY_EXISTS(HttpStatus.NOT_FOUND, 2001, Code.ALREADY_EXISTS, "User already exists"),
    INVALID_TOKEN(HttpStatus.FORBIDDEN, 2002, Code.PERMISSION_DENIED, "Invalid token"),
    BUSINESS_TYPE_NOT_FOUND(HttpStatus.NOT_FOUND, 3000, Code.INVALID_ARGUMENT, "Business type not found"),
    BUSINESS_TYPE_ALREADY_EXISTS(HttpStatus.FORBIDDEN, 3001, Code.ALREADY_EXISTS, "Business type already exists");

    private final HttpStatus httpStatus;
    private final int code;
    private final Code grpcCode;
    private final String message;
}
