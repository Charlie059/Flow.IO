package org.flowio.tenant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.flowio.tenant.dto.request.UserCreateRequest;
import org.flowio.tenant.dto.request.UserLoginRequest;
import org.flowio.tenant.entity.Tenant;
import org.flowio.tenant.entity.User;

import java.util.List;

public interface UserService extends IService<User> {
    List<User> getByEmail(String email);

    User getByEmailAndTenant(String email, Tenant tenant);

    User create(UserCreateRequest request);

    User login(UserLoginRequest request);
}
