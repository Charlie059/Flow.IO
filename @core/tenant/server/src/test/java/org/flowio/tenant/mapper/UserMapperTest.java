package org.flowio.tenant.mapper;

import com.baomidou.mybatisplus.test.autoconfigure.MybatisPlusTest;
import org.flowio.tenant.entity.User;
import org.flowio.tenant.entity.enums.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@MybatisPlusTest
class UserMapperTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    void testInsert() {
        final Timestamp now = Timestamp.valueOf(LocalDateTime.now());

        final User user = User.builder()
            .email("test@example.com")
            .name("test user")
            .password("password")
            .tenantId(1L)
            .roles(List.of(Role.ADMIN))
            .createdAt(now)
            .updatedAt(now)
            .build();

        userMapper.insert(user);

        assertNotNull(user.getId());
    }
}
