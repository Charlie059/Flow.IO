package org.flowio.tenant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.flowio.tenant.dto.request.UserCreateRequest;
import org.flowio.tenant.entity.Tenant;
import org.flowio.tenant.entity.User;

public interface UserService extends IService<User> {
    User getByEmail(String email);

    User getByEmailAndTenant(String email, Tenant tenant);

    User create(UserCreateRequest request);
}
