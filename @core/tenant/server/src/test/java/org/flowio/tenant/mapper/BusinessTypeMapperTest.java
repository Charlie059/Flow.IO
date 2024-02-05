package org.flowio.tenant.mapper;

import com.baomidou.mybatisplus.test.autoconfigure.MybatisPlusTest;
import org.flowio.tenant.entity.BusinessType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@MybatisPlusTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
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
    }
}
