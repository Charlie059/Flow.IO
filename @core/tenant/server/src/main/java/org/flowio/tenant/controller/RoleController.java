package org.flowio.tenant.controller;

import lombok.RequiredArgsConstructor;
import org.flowio.tenant.dto.BusinessTypeDto;
import org.flowio.tenant.entity.Response;
import org.flowio.tenant.entity.enums.Role;
import org.flowio.tenant.service.BusinessTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
class RoleController {
    /**
     * List all the roles
     *
     * @return {@link Response} of the list of roles
     */
    @GetMapping("")
    ResponseEntity<Response<List<Role>>> list() {
        var roles = List.of(Role.values());
        return ResponseEntity.ok(Response.success(roles));
    }
}
