package org.flowio.tenant.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@TableName("tenants")
public class Tenant {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private String name;
    private BusinessType businessType;
    private Timestamp createTime;
    private Timestamp updateTime;
}
