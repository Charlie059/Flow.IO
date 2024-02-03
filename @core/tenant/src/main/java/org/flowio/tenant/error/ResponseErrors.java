package org.flowio.tenant.error;

import lombok.experimental.UtilityClass;
import org.flowio.tenant.entity.Response;

/**
 * This is basically an enum class that covers all the possible errors that can be returned by the API
 */
@UtilityClass
public class ResponseErrors {
    public static final Response TENANT_NOT_FOUND = Response.error(1000, "Tenant not found");
    public static final Response TENANT_NAME_ALREADY_EXISTS = Response.error(1001, "Tenant name already exists");
    public static final Response USER_NOT_FOUND = Response.error(2000, "User not found");
    public static final Response USER_USERNAME_ALREADY_EXISTS = Response.error(2001, "User with this username already exists");
    public static final Response BUSINESS_TYPE_NOT_FOUND = Response.error(3000, "Business type not found");
    public static final Response BUSINESS_TYPE_ALREADY_EXISTS = Response.error(3001, "Business type already exists");
}
