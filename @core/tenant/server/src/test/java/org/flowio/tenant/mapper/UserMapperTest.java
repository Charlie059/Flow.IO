package org.flowio.tenant.mapper;

import com.baomidou.mybatisplus.test.autoconfigure.MybatisPlusTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

@MybatisPlusTest
@DirtiesContext
class UserMapperTest {
    @Autowired
    private UserMapper userMapper;
}
