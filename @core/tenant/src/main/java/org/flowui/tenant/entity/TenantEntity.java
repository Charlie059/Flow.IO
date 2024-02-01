package org.flowui.tenant.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@Builder
@RequiredArgsConstructor
public class TenantEntity {
    @Id
    @GeneratedValue
    private Long tenantId;
    private String tenantName;
}
