package org.flowio.tenant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
}
