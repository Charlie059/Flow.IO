package org.flowio.tenant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.flowio.tenant.dto.request.TenantCreateRequest;
import org.flowio.tenant.dto.request.TenantUpdateRequest;
import org.flowio.tenant.entity.BusinessType;
import org.flowio.tenant.entity.Tenant;
import org.flowio.tenant.exception.BusinessTypeNotFoundException;
import org.flowio.tenant.exception.TenantExistException;
import org.flowio.tenant.exception.TenantNotFoundException;
import org.flowio.tenant.mapper.TenantMapper;
import org.flowio.tenant.service.BusinessTypeService;
import org.flowio.tenant.service.TenantService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TenantServiceImpl extends ServiceImpl<TenantMapper, Tenant> implements TenantService {
    private final BusinessTypeService businessTypeService;

    @Override
    public Tenant getByIdOrThrow(Long id) throws TenantNotFoundException {
        Tenant tenant = super.getById(id);
        if (tenant == null) {
            throw new TenantNotFoundException();
        }
        return tenant;
    }

    @Override
    public Tenant getByName(String name) {
        return getOne(
            new LambdaQueryWrapper<Tenant>()
                .eq(Tenant::getName, name)
        );
    }

    @Override
    public Tenant getByNameOrThrow(String name) throws TenantNotFoundException {
        Tenant tenant = getByName(name);
        if (tenant == null) {
            throw new TenantNotFoundException();
        }
        return tenant;
    }

    @Override
    public Tenant create(TenantCreateRequest request) {
        // check if business type exists
        BusinessType businessType = businessTypeService.getById(request.getBusinessTypeId());
        if (businessType == null) {
            throw new BusinessTypeNotFoundException();
        }

        // check if tenant name exists
        Tenant existingTenant = getByName(request.getName());
        if (existingTenant != null) {
            throw new TenantExistException(existingTenant.getId());
        }

        // create tenant and store
        Tenant tenant = Tenant.builder()
            .name(request.getName())
            .businessTypeId(businessType.getId())
            .build();
        save(tenant);

        return tenant;
    }

    @Override
    public Tenant update(Tenant tenant, TenantUpdateRequest request) throws BusinessTypeNotFoundException {

        // argument checks
        if (request.getBusinessTypeId() != null) {
            // check if business type exists
            BusinessType businessType = businessTypeService.getById(request.getBusinessTypeId());
            if (businessType == null) {
                throw new BusinessTypeNotFoundException();
            }
        }

        // update tenant
        if (request.getName() != null) {
            tenant.setName(request.getName());
        }
        if (request.getBusinessTypeId() != null) {
            tenant.setBusinessTypeId(request.getBusinessTypeId());
        }
        tenant.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        update(tenant, new LambdaUpdateWrapper<Tenant>()
            .eq(Tenant::getId, tenant.getId())
        );

        return tenant;
    }
}
