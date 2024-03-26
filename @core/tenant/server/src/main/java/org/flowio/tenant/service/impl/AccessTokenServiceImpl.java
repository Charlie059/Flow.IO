package org.flowio.tenant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.flowio.tenant.entity.AccessToken;
import org.flowio.tenant.entity.User;
import org.flowio.tenant.mapper.AccessTokenMapper;
import org.flowio.tenant.service.AccessTokenService;
import org.flowio.tenant.service.JwtService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AccessTokenServiceImpl extends ServiceImpl<AccessTokenMapper, AccessToken> implements AccessTokenService {
    private final JwtService jwtService;

    @Override
    public AccessToken createToken(User user) {
        Map<String, Object> claims = Map.of(
            "email", user.getEmail()
        );

        String jwt = jwtService.generateToken(user, claims);

        AccessToken token = AccessToken.builder()
            .token(jwt)
            .userId(user.getId())
            .createdAt(new Timestamp(jwtService.extractCreatedAt(jwt)))
            .expiresAt(new Timestamp(jwtService.extractExpiresAt(jwt)))
            .build();
        save(token);
        return token;
    }

    @Override
    public AccessToken findByToken(String token) {
        return getOne(new LambdaQueryWrapper<AccessToken>()
            .eq(AccessToken::getToken, token));
    }

    @Override
    public boolean isTokenValid(AccessToken token) {
        if (token == null) {
            return false;
        }
        if (token.getExpiresAt().before(new Date())) {
            return false;
        }
        return !token.isRevoked();
    }
}
