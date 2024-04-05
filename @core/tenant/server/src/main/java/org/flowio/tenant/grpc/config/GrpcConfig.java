package org.flowio.tenant.grpc.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.security.authentication.BearerAuthenticationReader;
import net.devh.boot.grpc.server.security.authentication.GrpcAuthenticationReader;
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
            var accessToken = accessTokenService.getByToken(token);
            if (accessToken == null || !accessTokenService.isTokenValid(accessToken)) {
                return null;
            }

            var user = userService.getById(accessToken.getUserId());
            if (user == null) {
                return null;
            }
            log.info("The user {} in tenant {} provided a valid token.", user.getEmail(), user.getTenantId());
            log.info("User has the following permissions: {}", user.getAuthorities());
            var auth = SecurityContextHolder.getContext().getAuthentication();
            log.info("Current authentication: {}", auth);
            return new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                null,
                user.getAuthorities()
            );
        });
    }
}
