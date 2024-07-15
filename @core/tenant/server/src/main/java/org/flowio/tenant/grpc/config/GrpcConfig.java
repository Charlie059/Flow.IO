package org.flowio.tenant.grpc.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.security.authentication.BearerAuthenticationReader;
import net.devh.boot.grpc.server.security.authentication.GrpcAuthenticationReader;
import org.flowio.tenant.entity.AccessToken;
import org.flowio.tenant.entity.User;
import org.flowio.tenant.service.AccessTokenService;
import org.flowio.tenant.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class GrpcConfig {
    private final AccessTokenService accessTokenService;
    private final UserService userService;

    @Bean
    public GrpcAuthenticationReader grpcAuthenticationReader() {
        return new BearerAuthenticationReader(token -> {
            final AccessToken accessToken = accessTokenService.getByToken(token);
            if (accessToken != null && accessTokenService.isTokenValid(accessToken)
                && SecurityContextHolder.getContext().getAuthentication() == null
            ) {
                final User user = userService.getById(accessToken.getUserId());

                var authToken = new UsernamePasswordAuthenticationToken(
                    user,
                    user.getPassword(),
                    user.getAuthorities()
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

            return null;
        });
    }
}
