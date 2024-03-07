package org.flowio.tenant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.flowio.tenant.entity.BusinessType;
import org.flowio.tenant.entity.Tenant;

public interface TenantService extends IService<Tenant> {
    Tenant getByName(String name);

    Tenant create(String name, BusinessType businessType);
}
