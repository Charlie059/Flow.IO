package org.flowio.tenant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.flowio.tenant.dto.request.TenantCreateRequest;
import org.flowio.tenant.entity.Tenant;

public interface TenantService extends IService<Tenant> {
    Tenant getByName(String name);

    Tenant create(TenantCreateRequest request);
}
