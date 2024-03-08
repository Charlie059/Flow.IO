package org.flowio.tenant.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.flowio.tenant.dto.request.TenantCreateRequest;
import org.flowio.tenant.dto.response.TenantCreateResponse;
import org.flowio.tenant.entity.Response;
import org.flowio.tenant.entity.Tenant;
import org.flowio.tenant.exception.TenantNotFoundException;
import org.flowio.tenant.service.TenantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tenants")
@RequiredArgsConstructor
class TenantController {
    private final TenantService tenantService;

    @PostMapping("")
    ResponseEntity<Response<TenantCreateResponse>> createTenant(@Valid @RequestBody TenantCreateRequest request) {
        Tenant tenant = tenantService.create(request);

        return new ResponseEntity<>(
            Response.success(TenantCreateResponse.builder().id(tenant.getId()).build()),
            HttpStatus.CREATED
        );
    }

    @GetMapping("/{tenantId}")
    ResponseEntity<Response<Tenant>> getTenant(@PathVariable("tenantId") Long tenantId) {
        Tenant tenant = tenantService.getByIdOrThrow(tenantId);

        return ResponseEntity.ok(Response.success(tenant));
    }
}
