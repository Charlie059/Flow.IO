package org.flowio.tenant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.flowio.tenant.entity.User;

public interface IUserService extends IService<User> {
    User getByEmail(String email);
}
