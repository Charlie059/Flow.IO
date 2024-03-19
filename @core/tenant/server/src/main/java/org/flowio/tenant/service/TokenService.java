package org.flowio.tenant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.flowio.tenant.entity.Token;
import org.flowio.tenant.entity.User;

public interface TokenService extends IService<Token> {
    Token createToken(User user);

    Token findByToken(String token);

    boolean isTokenValid(Token token);
}
