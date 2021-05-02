package com.mszx.hyb.common.dao;

import com.mszx.hyb.common.entity.HybPlatformSecret;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.Cacheable;

public interface HybPlatformSecretMapper {
    @Cacheable(value = "platform_secret", key = "#p0")
    public HybPlatformSecret selectByPrimaryKey(@Param("platform_key") String platform_key, @Param("secret") String secret);
}
