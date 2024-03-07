package org.flowio.tenant.mapper;

import com.baomidou.mybatisplus.test.autoconfigure.MybatisPlusTest;
import org.flowio.tenant.entity.BusinessType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@MybatisPlusTest
class BusinessTypeMapperTest {
    @Autowired
    private BusinessTypeMapper businessTypeMapper;

    @Test
    void testInsert() {
        String businessTypeName = "Business";

        BusinessType businessType = BusinessType.builder()
            .name(businessTypeName)
            .build();

        businessTypeMapper.insert(businessType);

        assertNotNull(businessType.getId());
        assertEquals(businessTypeName, businessType.getName());
    }
}
