package org.flowio.tenant.mapper;

import com.baomidou.mybatisplus.test.autoconfigure.MybatisPlusTest;
import org.flowio.tenant.entity.BusinessType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@MybatisPlusTest
class BusinessTypeMapperTest {
    @Autowired
    private BusinessTypeMapper businessTypeMapper;

    @Test
    void testInsert() {
        final String businessTypeName = "Business";
        final Timestamp now = Timestamp.valueOf(LocalDateTime.now());

        final BusinessType businessType = BusinessType.builder()
            .name(businessTypeName)
            .createdAt(now) // dont know why CustomMetaObjectHandler is not triggered while testing
            .updatedAt(now)
            .build();

        businessTypeMapper.insert(businessType);

        assertNotNull(businessType.getId());
        assertEquals(businessTypeName, businessType.getName());
    }
}
