package org.flowio.tenant.handler;

import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import net.sf.jsqlparser.expression.Expression;

import java.util.List;

public class MultiTenantHandler implements TenantLineHandler {
    private static final List<String> MULTI_TENANT_IGNORED_TABLES = List.of("tenants", "business_types");

    @Override
    public Expression getTenantId() {
        return null;
    }

    @Override
    public boolean ignoreTable(String tableName) {
        return MULTI_TENANT_IGNORED_TABLES.contains(tableName);
    }
}
