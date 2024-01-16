package cloud.sparkflows.tenant.repository;

import cloud.sparkflows.tenant.model.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TenantRepository extends JpaRepository<Tenant, UUID> {

}
