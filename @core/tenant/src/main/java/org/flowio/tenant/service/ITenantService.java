package org.flowio.tenant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.flowio.tenant.entity.Tenant;

public interface ITenantService extends IService<Tenant> {
    Tenant getByName(String name);
}
