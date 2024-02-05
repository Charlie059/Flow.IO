package org.flowio.tenant.mapper;

import com.baomidou.mybatisplus.test.autoconfigure.MybatisPlusTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

@MybatisPlusTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserMapperTest {
    @Autowired
    private UserMapper userMapper;
}
