package org.flowio.tenant.handler;

import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.core.type.TypeReference;
import org.flowio.tenant.entity.enums.Role;

import java.io.IOException;
import java.util.List;

/**
 * The default {@link JacksonTypeHandler} cannot handle the element type of the list,
 * so we need to create a custom type handler.
 */
public class RoleListTypeHandler extends JacksonTypeHandler {

    public RoleListTypeHandler(Class<?> type) {
        super(type);
    }

    @Override
    public Object parse(String json) {
        try {
            return getObjectMapper().readValue(json, new TypeReference<List<Role>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
