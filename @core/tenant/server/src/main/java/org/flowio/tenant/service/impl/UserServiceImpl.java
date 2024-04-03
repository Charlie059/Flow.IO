package org.flowio.tenant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowio.tenant.dto.request.UserCreateRequest;
import org.flowio.tenant.dto.request.UserLoginRequest;
import org.flowio.tenant.entity.Tenant;
import org.flowio.tenant.entity.User;
import org.flowio.tenant.entity.enums.Role;
import org.flowio.tenant.exception.InvalidCredentialsException;
import org.flowio.tenant.exception.TenantNotFoundException;
import org.flowio.tenant.exception.UserExistException;
import org.flowio.tenant.mapper.UserMapper;
import org.flowio.tenant.service.TenantService;
import org.flowio.tenant.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final TenantService tenantService;

    @Override
    public List<User> getByEmail(String email) {
        return list(
            new LambdaQueryWrapper<User>()
                .eq(User::getEmail, email)
        );
    }

    @Override
    public User getByEmailAndTenant(String email, Tenant tenant) {
        return getOne(
            new LambdaQueryWrapper<User>()
                .eq(User::getEmail, email)
                .eq(User::getTenantId, tenant.getId())
        );
    }

    @Override
    public User create(UserCreateRequest request) {
        // check if tenant exists
        Tenant tenant = tenantService.getById(request.getTenantId());
        if (tenant == null) {
            throw new TenantNotFoundException();
        }

        // check if user email exists within tenant
        User existingUser = getByEmailAndTenant(request.getEmail(), tenant);
        if (existingUser != null) {
            throw new UserExistException();
        }

        // create user and store
        User user = User.builder()
            .email(request.getEmail())
            .name(request.getName())
            .password(passwordEncoder.encode(request.getPassword()))
            .tenantId(request.getTenantId())
            .role(Role.valueOf(request.getRole()))
            .build();
        save(user);
        return user;
    }

    @Override
    public User createAdmin(Tenant tenant, String email, String password) {
        return create(UserCreateRequest.builder()
            .tenantId(tenant.getId())
            .email(email)
            .name(tenant.getName() + " admin")
            .password(password)
            .role("ADMIN")
            .build());
    }

    @Override
    public User login(UserLoginRequest request) {
        // check if tenant exists
        Tenant tenant = tenantService.getById(request.getTenantId());
        if (tenant == null) {
            throw new TenantNotFoundException();
        }

        // the username is manually concatenated with user id and tenant id
        // so we need to retrieve the user by email and tenant first
        User user = getByEmailAndTenant(request.getEmail(), tenant);
        if (user == null) {
            log.info("user not found with given email and tenant: {} and {}", request.getEmail(), tenant.getName());
            throw new InvalidCredentialsException();
        }

        // authenticate user
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    user.getTenantId() + ":" + user.getId(),
                    request.getPassword()
                )
            );
        } catch (AuthenticationException ex) {
            log.error("Invalid credentials", ex);
            // use our own exception
            throw new InvalidCredentialsException();
        }
        return getByEmailAndTenant(request.getEmail(), tenant);
    }
}
