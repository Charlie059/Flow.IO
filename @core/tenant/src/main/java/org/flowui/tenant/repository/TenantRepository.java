package org.flowui.tenant.repository;

import org.flowui.tenant.entity.TenantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantRepository extends JpaRepository<TenantEntity, Long> {
}
