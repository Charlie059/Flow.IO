package org.flowio.tenant.exception;

public class TenantNotFoundException extends RuntimeException {
    public TenantNotFoundException(Long id) {
        super("Cannot find tenant with id: " + id);
    }
}
