package org.flowio.tenant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.flowio.tenant.entity.AccessToken;
import org.flowio.tenant.entity.User;

public interface AccessTokenService extends IService<AccessToken> {
    AccessToken createToken(User user);

    AccessToken findByToken(String token);

    boolean isTokenValid(AccessToken token);
}
