package org.flowio.tenant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.flowio.tenant.dto.request.TenantCreateRequest;
import org.flowio.tenant.dto.request.TenantUpdateRequest;
import org.flowio.tenant.entity.BusinessType;
import org.flowio.tenant.entity.Tenant;
import org.flowio.tenant.exception.BusinessTypeNotFoundException;
import org.flowio.tenant.exception.TenantExistException;
import org.flowio.tenant.exception.TenantNotFoundException;

public interface TenantService extends IService<Tenant> {
    /**
     * Get {@link Tenant} by id, or throw exception if not found
     *
     * @param id The id of {@link Tenant}.
     * @return {@link Tenant} with the given id
     * @throws TenantNotFoundException if {@link Tenant} not found
     */
    Tenant getByIdOrThrow(Long id) throws TenantNotFoundException;

    /**
     * Get {@link Tenant} by name
     *
     * @param name The name of {@link Tenant}
     * @return {@link Tenant} with the given name
     */
    Tenant getByName(String name);

    /**
     * Get {@link Tenant} by name, or throw exception if not found
     *
     * @param name The name of {@link Tenant}
     * @return {@link Tenant} with the given name
     * @throws TenantNotFoundException if {@link Tenant} not found
     */
    Tenant getByNameOrThrow(String name) throws TenantNotFoundException;

    /**
     * Create a new {@link Tenant}
     *
     * @param request {@link TenantCreateRequest}
     * @return created {@link Tenant}
     * @throws TenantExistException          if {@link Tenant} already exists
     * @throws BusinessTypeNotFoundException if {@link BusinessType} not found
     */
    Tenant create(TenantCreateRequest request) throws TenantExistException, BusinessTypeNotFoundException;

    /**
     * Update {@link Tenant}.
     *
     * @param request {@link TenantUpdateRequest}
     * @return updated {@link Tenant}
     * @throws BusinessTypeNotFoundException if {@link BusinessType} not found
     */
    Tenant update(Tenant tenant, TenantUpdateRequest request) throws BusinessTypeNotFoundException;
}
