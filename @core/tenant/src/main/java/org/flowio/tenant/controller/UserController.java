package org.flowio.tenant.controller;

import lombok.RequiredArgsConstructor;
import org.flowio.tenant.dto.request.UserCreateRequest;
import org.flowio.tenant.dto.response.UserCreateResponse;
import org.flowio.tenant.entity.Response;
import org.flowio.tenant.service.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;

    @PostMapping("")
    ResponseEntity<Response<UserCreateResponse>> newUser(@RequestBody UserCreateRequest dto) {
        // TODO: finish user creation

        return new ResponseEntity<>(
            Response.success(),
            HttpStatus.CREATED
        );
    }
}
