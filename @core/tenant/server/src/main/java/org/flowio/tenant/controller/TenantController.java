package org.flowio.tenant.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.flowio.tenant.dto.request.TenantCreateRequest;
import org.flowio.tenant.dto.response.TenantCreateResponse;
import org.flowio.tenant.entity.Response;
import org.flowio.tenant.entity.Tenant;
import org.flowio.tenant.service.RngService;
import org.flowio.tenant.service.TenantService;
import org.flowio.tenant.service.UserService;
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
    private final UserService userService;
    private final RngService rngService;

    /**
     * Create a new tenant.
     *
     * @param request {@link TenantCreateRequest}
     * @return {@link Response} of the created tenant id.
     */
    @PostMapping("")
    ResponseEntity<Response<TenantCreateResponse>> createTenant(@Valid @RequestBody TenantCreateRequest request) {
        Tenant tenant = tenantService.create(request);

        // create admin user
        var adminPassword = rngService.randomPassword(16);
        var adminUser = userService.createAdmin(tenant, request.getAdminEmail(), adminPassword);

        var response = TenantCreateResponse.builder()
            .id(tenant.getId())
            .adminUser(TenantCreateResponse.TenantAdminUser.builder()
                .id(adminUser.getId())
                .email(adminUser.getEmail())
                .password(adminPassword)
                .build())
            .build();

        return new ResponseEntity<>(
            Response.success(response),
            HttpStatus.CREATED
        );
    }

    /**
     * Get a tenant by id.
     *
     * @param tenantId tenant id
     * @return {@link Response} of the tenant.
     */
    @GetMapping("/{tenantId}")
    ResponseEntity<Response<Tenant>> getTenant(@PathVariable("tenantId") Long tenantId) {
        Tenant tenant = tenantService.getByIdOrThrow(tenantId);

        return ResponseEntity.ok(Response.success(tenant));
    }
}
