package org.flowio.tenant.config;

import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.security.authentication.BasicGrpcAuthenticationReader;
import net.devh.boot.grpc.server.security.authentication.GrpcAuthenticationReader;
import org.flowio.tenant.filter.TokenAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private final TokenAuthenticationFilter tokenAuthenticationFilter;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain applicationSecurity(HttpSecurity security) throws Exception {
        return security
            .csrf(CsrfConfigurer::disable)
            .formLogin(FormLoginConfigurer::disable)
            .authorizeHttpRequests(configurer -> configurer
                .anyRequest().permitAll()
            )
            .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .logout(configurer -> configurer
                .logoutUrl("/api/v1/auth/logout")
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
            )
            .build();
    }

    @Bean
    public GrpcAuthenticationReader grpcAuthenticationReader() {
        return new BasicGrpcAuthenticationReader();
    }
}
