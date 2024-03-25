package org.flowio.tenant.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.flowio.tenant.entity.AccessToken;

@Mapper
public interface AccessTokenMapper extends BaseMapper<AccessToken> {
}
