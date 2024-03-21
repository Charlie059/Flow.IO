package org.flowio.tenant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.flowio.tenant.entity.Token;
import org.flowio.tenant.entity.User;
import org.flowio.tenant.mapper.TokenMapper;
import org.flowio.tenant.service.JwtService;
import org.flowio.tenant.service.TokenService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl extends ServiceImpl<TokenMapper, Token> implements TokenService {
    private final JwtService jwtService;

    @Override
    public Token createToken(User user) {
        Map<String, Object> claims = Map.of(
            "email", user.getEmail()
        );

        String jwt = jwtService.generateToken(user, claims);

        Token token = Token.builder()
            .token(jwt)
            .userId(user.getId())
            .createdAt(new Timestamp(jwtService.extractCreatedAt(jwt)))
            .expiresAt(new Timestamp(jwtService.extractExpiresAt(jwt)))
            .build();
        save(token);
        return token;
    }

    @Override
    public Token findByToken(String token) {
        return getOne(new LambdaQueryWrapper<Token>()
            .eq(Token::getToken, token));
    }

    @Override
    public boolean isTokenValid(Token token) {
        if (token == null) {
            return false;
        }
        if (token.getExpiresAt().before(new Date())) {
            return false;
        }
        return !token.isRevoked();
    }
}
