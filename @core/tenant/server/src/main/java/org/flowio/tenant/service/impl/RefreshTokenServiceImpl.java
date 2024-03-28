package org.flowio.tenant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.flowio.tenant.config.TokenConfig;
import org.flowio.tenant.entity.RefreshToken;
import org.flowio.tenant.entity.User;
import org.flowio.tenant.mapper.RefreshTokenMapper;
import org.flowio.tenant.service.RefreshTokenService;
import org.flowio.tenant.service.TokenService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl extends ServiceImpl<RefreshTokenMapper, RefreshToken> implements RefreshTokenService {
    private final TokenConfig tokenConfig;
    private final TokenService tokenService;

    @Override
    public RefreshToken createToken(User user) {
        String token = tokenService.generateToken();

        RefreshToken refreshToken = RefreshToken.builder()
            .token(token)
            .userId(user.getId())
            .createdAt(new Timestamp(System.currentTimeMillis()))
            .expiresAt(new Timestamp(System.currentTimeMillis() + tokenConfig.getRefreshTokenExpiresIn()))
            .build();
        save(refreshToken);
        return refreshToken;
    }

    @Override
    public RefreshToken getByToken(String token) {
        return getOne(new LambdaQueryWrapper<RefreshToken>()
            .eq(RefreshToken::getToken, token));
    }

    @Override
    public boolean isTokenValid(RefreshToken token) {
        if (token == null) {
            return false;
        }
        if (token.getCreatedAt().after(new Date())) {
            return false;
        }
        return !token.getExpiresAt().before(new Date());
    }
}
