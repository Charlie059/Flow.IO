package org.flowio.tenant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.flowio.tenant.dto.request.UserCreateRequest;
import org.flowio.tenant.entity.Tenant;
import org.flowio.tenant.entity.User;
import org.flowio.tenant.mapper.UserMapper;
import org.flowio.tenant.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public User getByEmail(String email) {
        return getOne(
            new LambdaQueryWrapper<User>()
                .eq(User::getEmail, email)
        );
    }

    @Override
    public User getByEmailAndTenant(String email, Tenant tenant) {
        return getOne(
            new LambdaQueryWrapper<User>()
                .eq(User::getEmail, email)
                .eq(User::getTenantId, tenant.getId())
        );
    }

    @Override
    public User create(UserCreateRequest request) {
        User user = User.builder()
            .email(request.getEmail())
            .password(request.getPassword())
            .tenantId(request.getTenantId())
            .build();
        save(user);
        return user;
    }
}
