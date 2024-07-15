package org.flowio.tenant.controller;

import lombok.RequiredArgsConstructor;
import org.flowio.tenant.dto.BusinessTypeDto;
import org.flowio.tenant.entity.Response;
import org.flowio.tenant.service.BusinessTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/business-types")
@RequiredArgsConstructor
class BusinessTypeController {
    private final BusinessTypeService businessTypeService;

    /**
     * List all business types.
     *
     * @return {@link Response} of the list of business types (id and name).
     */
    @GetMapping("")
    ResponseEntity<Response<List<BusinessTypeDto>>> list() {
        final var businessTypes = businessTypeService.list().stream()
            .map(businessType -> BusinessTypeDto.builder()
                .id(businessType.getId())
                .name(businessType.getName())
                .build()
            )
            .toList();
        return ResponseEntity.ok(Response.success(businessTypes));
    }
}
