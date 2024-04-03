package org.flowio.tenant.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Component
@Slf4j
public class CustomMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        this.strictInsertFill(metaObject, "createdAt", () -> Timestamp.valueOf(LocalDateTime.now()), Timestamp.class);
        this.strictInsertFill(metaObject, "updatedAt", () -> Timestamp.valueOf(LocalDateTime.now()), Timestamp.class);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        this.strictUpdateFill(metaObject, "updatedAt", () -> Timestamp.valueOf(LocalDateTime.now()), Timestamp.class);
    }
}
