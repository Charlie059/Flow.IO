package org.flowio.authenticationservice.config;


import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.flowio.authenticationservice.Entities.LoginUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final LoginUserRepository loginUserRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> loginUserRepository.findByLoginEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found by user Email."));
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    @NonNull
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    @NonNull
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

