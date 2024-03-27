package org.flowio.tenant.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.flowio.tenant.entity.RefreshToken;

@Mapper
public interface RefreshTokenMapper extends BaseMapper<RefreshToken> {
}
