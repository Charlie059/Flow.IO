package org.flowio.tenant.controller;

import lombok.RequiredArgsConstructor;
import org.flowio.tenant.entity.Response;
import org.flowio.tenant.entity.Tenant;
import org.flowio.tenant.entity.User;
import org.flowio.tenant.error.ResponseErrors;
import org.flowio.tenant.proto.UserCreateRequest;
import org.flowio.tenant.proto.UserCreateResponse;
import org.flowio.tenant.service.TenantService;
import org.flowio.tenant.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final TenantService tenantService;
    private final UserService userService;

    @PostMapping("")
    ResponseEntity<Response<UserCreateResponse>> newUser(UserCreateRequest request) {
        Tenant tenant = tenantService.getById(request.getTenantId());
        if (tenant == null) {
            return new ResponseEntity<>(ResponseErrors.TENANT_NOT_FOUND.toResponse(), HttpStatus.UNPROCESSABLE_ENTITY);
        }

        User user = userService.create(request.getEmail(), request.getPassword(), tenant);

        return new ResponseEntity<>(
            Response.success(UserCreateResponse.newBuilder().setId(user.getId()).build()),
            HttpStatus.CREATED
        );
    }
}
