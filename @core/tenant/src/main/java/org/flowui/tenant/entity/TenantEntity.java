package org.flowui.tenant.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@RequiredArgsConstructor
public class TenantEntity {
    @Id
    private Long tenantId;
    private String tenantName;
}
