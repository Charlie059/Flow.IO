package org.flowio.tenant.mapper;

import com.baomidou.mybatisplus.test.autoconfigure.MybatisPlusTest;
import org.flowio.tenant.entity.Tenant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@MybatisPlusTest
@DirtiesContext
class TenantMapperTest {
    @Autowired
    private TenantMapper tenantMapper;

    @Test
    void testInsert() {
        final Timestamp now = Timestamp.valueOf(LocalDateTime.now());

        final Tenant tenant = Tenant.builder()
            .name("Tenant")
            .businessTypeId(1)
            .createdAt(now)
            .updatedAt(now)
            .build();

        tenantMapper.insert(tenant);

        assertNotNull(tenant.getId());
    }
}
