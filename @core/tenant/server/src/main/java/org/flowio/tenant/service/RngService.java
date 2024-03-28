package org.flowio.tenant.service;

public interface RngService {
    /**
     * Generate a random password with the given length
     *
     * @param length The length of the password
     * @return The generated password
     */
    String randomPassword(int length);
}
