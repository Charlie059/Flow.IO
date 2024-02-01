package org.flowui.tenant.controller;

import lombok.RequiredArgsConstructor;
import org.flowui.tenant.dto.TenantDto;
import org.flowui.tenant.entity.TenantEntity;
import org.flowui.tenant.repository.TenantRepository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tenants")
@RequiredArgsConstructor
class TenantController {
    private final TenantRepository repository;

    TenantEntity newTenant(@RequestBody TenantDto dto) {
        return repository.save();
    }
}
