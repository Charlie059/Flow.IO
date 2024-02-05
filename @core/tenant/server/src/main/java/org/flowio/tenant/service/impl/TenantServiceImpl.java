package org.flowio.tenant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.flowio.tenant.entity.Tenant;
import org.flowio.tenant.mapper.TenantMapper;
import org.flowio.tenant.service.ITenantService;
import org.springframework.stereotype.Service;

@Service
public class TenantServiceImpl extends ServiceImpl<TenantMapper, Tenant> implements ITenantService {

    @Override
    public Tenant getByName(String name) {
        return getOne(
            new LambdaQueryWrapper<Tenant>()
                .eq(Tenant::getName, name)
        );
    }
}
