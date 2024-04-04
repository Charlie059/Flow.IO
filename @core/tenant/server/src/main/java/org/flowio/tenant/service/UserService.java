package org.flowio.tenant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.flowio.tenant.dto.request.UserCreateRequest;
import org.flowio.tenant.dto.request.UserLoginRequest;
import org.flowio.tenant.entity.Tenant;
import org.flowio.tenant.entity.User;

import java.util.List;

public interface UserService extends IService<User> {
    /**
     * Get the list of {@link User} by email. The email may be used in multiple tenants.
     *
     * @param email The email of {@link User}
     * @return The list of {@link User} with the given email
     */
    List<User> getByEmail(String email);

    /**
     * Get {@link User} by email and tenant
     *
     * @param email  The email of {@link User}
     * @param tenant The tenant of {@link User}
     * @return The {@link User} with the given email and tenant
     */
    User getByEmailAndTenant(String email, Tenant tenant);

    /**
     * Get the list of {@link User}s by tenant
     *
     * @param tenant The tenant
     * @return The list of {@link User}s from the tenant
     */
    List<User> getByTenant(Tenant tenant);

    /**
     * Create a new {@link User}
     *
     * @param request The {@link UserCreateRequest}
     * @return The created {@link User}
     */
    User create(UserCreateRequest request);

    /**
     * Create a new {@link User} as admin for the given {@link Tenant}
     *
     * @param tenant   The {@link Tenant} to create user for
     * @param email    The email of the user
     * @param password The password of the user
     * @return created {@link User}
     */
    User createAdmin(Tenant tenant, String email, String password);

    /**
     * Login with email and password
     *
     * @param request The {@link UserLoginRequest}
     * @return The {@link User} if login success, otherwise null
     */
    User login(UserLoginRequest request);
}
