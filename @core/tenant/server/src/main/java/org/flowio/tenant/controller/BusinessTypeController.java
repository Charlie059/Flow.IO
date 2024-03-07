package org.flowio.tenant.controller;

import lombok.RequiredArgsConstructor;
import org.flowio.tenant.entity.BusinessType;
import org.flowio.tenant.entity.Response;
import org.flowio.tenant.service.BusinessTypeService;
import org.springframework.http.HttpStatus;
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

    @GetMapping("")
    ResponseEntity<Response<List<BusinessType>>> list() {
        // check whether business type exists
        var businessTypes = businessTypeService.list();
        return new ResponseEntity<>(Response.success(businessTypes), HttpStatus.OK);
    }
}
