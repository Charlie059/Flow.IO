package org.flowio.tenant.controller;

import lombok.RequiredArgsConstructor;
import org.flowio.tenant.entity.BusinessType;
import org.flowio.tenant.entity.Response;
import org.flowio.tenant.entity.Tenant;
import org.flowio.tenant.error.ResponseErrors;
import org.flowio.tenant.proto.TenantCreateRequest;
import org.flowio.tenant.proto.TenantCreateResponse;
import org.flowio.tenant.service.BusinessTypeService;
import org.flowio.tenant.service.TenantService;
import org.flowio.tenant.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tenants")
@RequiredArgsConstructor
class TenantController {
    private final TenantService tenantService;
    private final BusinessTypeService businessTypeService;
    private final UserService userService;

    @PostMapping("")
    ResponseEntity<Response<TenantCreateResponse>> newTenant(TenantCreateRequest request) {
        // check whether business type exists
        BusinessType businessType = businessTypeService.getById(request.getBusinessTypeId());
        if (businessType == null) {
            return new ResponseEntity<>(ResponseErrors.BUSINESS_TYPE_NOT_FOUND.toResponse(), HttpStatus.UNPROCESSABLE_ENTITY);
        }

        // check whether tenant name already exists
        Tenant existingTenant = tenantService.getByName(request.getName());
        if (existingTenant != null) {
            return new ResponseEntity<>(ResponseErrors.TENANT_NAME_ALREADY_EXISTS.toResponse(), HttpStatus.CONFLICT);
        }

        // create new tenant
        Tenant tenant = tenantService.create(request.getName(), businessType);

        return new ResponseEntity<>(
            Response.success(TenantCreateResponse.newBuilder().setId(tenant.getId()).build()),
            HttpStatus.CREATED
        );
    }

    @GetMapping("/{tenantId}")
    ResponseEntity<Response<Tenant>> getTenant(@PathVariable("tenantId") Long tenantId) {
        Tenant tenant = tenantService.getById(tenantId);

        if (tenant != null) {
            return ResponseEntity.ok(Response.success(tenant));
        } else {
            return new ResponseEntity<>(ResponseErrors.TENANT_NOT_FOUND.toResponse(), HttpStatus.NOT_FOUND);
        }
    }
}
