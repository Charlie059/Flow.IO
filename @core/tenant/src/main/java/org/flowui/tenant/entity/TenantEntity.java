package org.flowui.tenant.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;

@Entity
@Data
@Builder
@RequiredArgsConstructor
public class TenantEntity {
    @Id
    @GeneratedValue
    Long tenantId;

    @Column(nullable = false)
    String tenantName;

    @Temporal(TemporalType.TIMESTAMP)
    Timestamp createdDate;

    @Column(nullable = false)
    String businessType; // TODO: Use enum or an invidual table
}
