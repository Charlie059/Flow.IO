package org.flowio.tenant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.flowio.tenant.dto.request.UserCreateRequest;
import org.flowio.tenant.entity.Tenant;
import org.flowio.tenant.entity.User;
import org.flowio.tenant.exception.TenantNotFoundException;
import org.flowio.tenant.exception.UserExistException;
import org.flowio.tenant.mapper.UserMapper;
import org.flowio.tenant.service.TenantService;
import org.flowio.tenant.service.UserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private final TenantService tenantService;

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
        // check if tenant exists
        Tenant tenant = tenantService.getById(request.getTenantId());
        if (tenant == null) {
            throw new TenantNotFoundException();
        }

        // check if user email exists within tenant
        User existingUser = getByEmailAndTenant(request.getEmail(), tenant);
        if (existingUser != null) {
            throw new UserExistException();
        }

        // create user and store
        User user = User.builder()
            .email(request.getEmail())
            .password(request.getPassword())
            .tenantId(request.getTenantId())
            .build();
        save(user);
        return user;
    }
}
