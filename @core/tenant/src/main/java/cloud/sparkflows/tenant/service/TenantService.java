package cloud.sparkflows.tenant.service;

import cloud.sparkflows.tenant.model.Tenant;
import cloud.sparkflows.tenant.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TenantService {

    @Autowired
    private TenantRepository tenantRepository;

    public Tenant createTenant(Tenant tenant) {
        return tenantRepository.save(tenant);
    }

    public Optional<Tenant> getTenant(UUID id) {
        return tenantRepository.findById(id);
    }

    public List<Tenant> getAllTenants() {
        return tenantRepository.findAll();
    }

    public Tenant updateTenant(UUID id, Tenant tenant) {
        tenant.setId(id);
        return tenantRepository.save(tenant);
    }

    public void deleteTenant(UUID id) {
        tenantRepository.deleteById(id);
    }
}
