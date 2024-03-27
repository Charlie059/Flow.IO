package org.flowio.tenant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.flowio.tenant.entity.RefreshToken;
import org.flowio.tenant.entity.User;

public interface RefreshTokenService extends IService<RefreshToken> {
    RefreshToken createToken(User user);

    RefreshToken findByToken(String token);

    boolean isTokenValid(RefreshToken token);
}
