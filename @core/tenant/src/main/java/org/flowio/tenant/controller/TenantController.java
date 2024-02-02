package org.flowio.tenant.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.flowio.tenant.dto.request.TenantCreateRequest;
import org.flowio.tenant.dto.response.TenantCreateResponse;
import org.flowio.tenant.entity.BusinessType;
import org.flowio.tenant.entity.Response;
import org.flowio.tenant.entity.Tenant;
import org.flowio.tenant.service.IBusinessTypeService;
import org.flowio.tenant.service.ITenantService;
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
    private final ITenantService tenantService;
    private final IBusinessTypeService businessTypeService;

    @PostMapping("")
    ResponseEntity<Response<TenantCreateResponse>> newTenant(@RequestBody TenantCreateRequest dto) {
        // check whether business type exists
        BusinessType businessType = businessTypeService.getById(dto.businessTypeId());
        if (businessType == null) {
            return new ResponseEntity<>(
                Response.error(2000, "Business type not found"),
                HttpStatus.FORBIDDEN
            );
        }

        // check whether tenant name already exists
        Tenant existingTenant = tenantService.getByName(dto.tenantName());
        if (existingTenant != null) {
            return new ResponseEntity<>(
                Response.error(1001, "Tenant name already exists"),
                HttpStatus.FORBIDDEN
            );
        }

        // create new tenant
        Tenant newTenant = Tenant.builder()
            .name(dto.tenantName())
            .businessType(businessType)
            .build();

        tenantService.save(newTenant);

        // get the created tenant
        Tenant tenant = tenantService.getOne(
            new LambdaQueryWrapper<Tenant>()
                .select(Tenant::getId)
                .eq(Tenant::getName, dto.tenantName())
        );

        return new ResponseEntity<>(
            Response.success(new TenantCreateResponse(tenant.getId())),
            HttpStatus.CREATED
        );
    }

    @GetMapping("/{tenantId}")
    ResponseEntity<Response<Tenant>> getTenant(@PathVariable("tenantId") Long tenantId) {
        Tenant tenant = tenantService.getById(tenantId);

        if (tenant != null) {
            return ResponseEntity.ok(Response.success(tenant));
        } else {
            return new ResponseEntity<>(
                Response.error(1000, "Tenant not found"),
                HttpStatus.NOT_FOUND
            );
        }
    }
}
