package com.lindj.boot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author lindj
 * @date 2019/5/30 0030
 * @description
 */
@Component
@ConfigurationProperties(prefix = "token")
@Data
public class JwtProperties {
    /**
     * token过期时间，单位分钟
     */

    private Integer tokenExpireTime;
    /**
     * 刷新Token过期时间，单位分钟
     */
    private Integer refreshTokenExpireTime;
    /**
     * Shiro缓存有效期，单位分钟
     */
    private Integer shiroCacheExpireTime;
    /**
     * token加密密钥
     */
    private String secretKey;
}
