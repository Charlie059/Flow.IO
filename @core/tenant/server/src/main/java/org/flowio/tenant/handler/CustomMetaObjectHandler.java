package org.flowio.tenant.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Component
public class CustomMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createdAt", Timestamp.valueOf(LocalDateTime.now()), metaObject);
        this.setFieldValByName("updatedAt", Timestamp.valueOf(LocalDateTime.now()), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updatedAt", Timestamp.valueOf(LocalDateTime.now()), metaObject);
    }
}
