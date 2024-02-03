package org.flowio.tenant.mapper;

import com.baomidou.mybatisplus.test.autoconfigure.MybatisPlusTest;
import org.flowio.tenant.entity.Tenant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@MybatisPlusTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TenantMapperTest {
    @Autowired
    private TenantMapper tenantMapper;

    @Test
    void testInsert() {
        Tenant tenant = Tenant.builder()
            .name("Tenant")
            .build();

        tenantMapper.insert(tenant);

        assertNotNull(tenant.getId());
    }
}
