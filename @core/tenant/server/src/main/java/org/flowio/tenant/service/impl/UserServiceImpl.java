package org.flowio.tenant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.flowio.tenant.entity.Tenant;
import org.flowio.tenant.entity.User;
import org.flowio.tenant.mapper.UserMapper;
import org.flowio.tenant.service.IUserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

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
                .eq(User::getTenant, tenant)
        );
    }

    @Override
    public User create(String email, String password, Tenant tenant) {
        User user = User.builder()
            .email(email)
            .password(password)
            .tenant(tenant)
            .build();
        save(user);
        return user;
    }
}
