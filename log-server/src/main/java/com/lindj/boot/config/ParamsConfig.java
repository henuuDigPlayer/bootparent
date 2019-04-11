package com.lindj.boot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author lindj
 * @date 2019/3/1
 * @description
 */
@Component
@ConfigurationProperties(prefix = "constants")
@Data
public class ParamsConfig {

    private Long writeMapExpire;
    private Long readMapExpire;
    private Integer writeMapSize;
    private Map<String, Object> threadPool;
}
