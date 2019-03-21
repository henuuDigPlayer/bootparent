package com.lindj.boot;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author lindj
 * @date 2019/3/21
 * @description
 */
@Component
@ConfigurationProperties(prefix = "server")
public class ConfigParams {

    private Integer port;

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
