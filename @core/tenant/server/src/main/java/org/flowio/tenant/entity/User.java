package org.flowio.tenant.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.flowio.tenant.dto.UserDto;
import org.flowio.tenant.entity.enums.Role;
import org.flowio.tenant.handler.RoleListTypeHandler;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "users", autoResultMap = true)
public class User implements UserDetails {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private String email;
    private String name;
    private String password;
    @TableField(value = "tenant_id")
    private Long tenantId;
    @TableField(typeHandler = RoleListTypeHandler.class)
    private List<Role> roles;
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private Timestamp createdAt;
    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    private Timestamp updatedAt;

    public UserDto toDto() {
        return UserDto.builder()
            .id(id)
            .email(email)
            .name(name)
            .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
            .flatMap(role -> role.getAuthorities().stream())
            .collect(Collectors.toSet());
    }

    @Override
    public String getUsername() {
        return tenantId + ":" + id;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
