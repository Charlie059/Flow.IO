package org.flowui.tenant.controller;

import lombok.RequiredArgsConstructor;
import org.flowui.tenant.dto.request.TenantCreateRequest;
import org.flowui.tenant.dto.response.TenantCreateResponse;
import org.flowui.tenant.entity.TenantEntity;
import org.flowui.tenant.repository.TenantRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tenants")
@RequiredArgsConstructor
class TenantController {
    private final TenantRepository repository;

    @PostMapping("/")
    ResponseEntity<TenantCreateResponse> newTenant(@RequestBody TenantCreateRequest dto) {
        TenantEntity tenant = repository.save(TenantEntity.builder()
            .tenantName(dto.tenantName())
            .build());

        return new ResponseEntity<>(
            TenantCreateResponse.builder()
                .tenantId(tenant.getTenantId())
                .message("Tenant created successfully")
                .build(),
            HttpStatus.CREATED
        );
    }

}
