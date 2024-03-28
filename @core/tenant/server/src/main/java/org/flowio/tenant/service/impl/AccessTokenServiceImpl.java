package org.flowio.tenant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.flowio.tenant.config.TokenConfig;
import org.flowio.tenant.entity.AccessToken;
import org.flowio.tenant.entity.User;
import org.flowio.tenant.mapper.AccessTokenMapper;
import org.flowio.tenant.service.AccessTokenService;
import org.flowio.tenant.service.TokenService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AccessTokenServiceImpl extends ServiceImpl<AccessTokenMapper, AccessToken> implements AccessTokenService {
    private final TokenConfig tokenConfig;
    private final TokenService tokenService;

    @Override
    public AccessToken createToken(User user) {
        String token = tokenService.generateToken();

        AccessToken accessToken = AccessToken.builder()
            .token(token)
            .userId(user.getId())
            .createdAt(new Timestamp(System.currentTimeMillis()))
            .expiresAt(new Timestamp(System.currentTimeMillis() + tokenConfig.getAccessTokenExpiresIn()))
            .build();
        save(accessToken);
        return accessToken;
    }

    @Override
    public AccessToken getByToken(String token) {
        return getOne(new LambdaQueryWrapper<AccessToken>()
            .eq(AccessToken::getToken, token));
    }

    @Override
    public boolean isTokenValid(AccessToken token) {
        if (token == null) {
            return false;
        }
        if (token.getCreatedAt().after(new Date())) {
            return false;
        }
        if (token.getExpiresAt().before(new Date())) {
            return false;
        }
        return !token.isRevoked();
    }
}
