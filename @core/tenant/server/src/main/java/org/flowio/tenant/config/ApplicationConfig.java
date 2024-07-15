package org.flowio.tenant.config;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowio.tenant.entity.User;
import org.flowio.tenant.mapper.UserMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.token.SecureRandomFactoryBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ApplicationConfig {
    private final UserMapper userMapper;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            String[] parts = username.split(":");
            if (parts.length != 2) {
                return null;
            }
            // no part checking here, just assume it's valid
            Long tenantId = Long.parseLong(parts[0]);
            Long userId = Long.parseLong(parts[1]);
            try {
                return userMapper.selectOne(new LambdaQueryWrapper<User>()
                    .eq(User::getId, userId)
                    .eq(User::getTenantId, tenantId)
                );
            } catch (Exception ex) {
                log.error("unexpected exception while retrieving UserDetails", ex);
                return null;
            }
        };
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(authenticationProvider());
    }

    @Bean
    public SecureRandomFactoryBean secureRandomFactoryBean() {
        var factory = new SecureRandomFactoryBean();
        factory.setAlgorithm("SHA1PRNG");
        return factory;
    }

    @Bean
    public SecureRandom secureRandom() throws Exception {
        return secureRandomFactoryBean().getObject();
    }
}
