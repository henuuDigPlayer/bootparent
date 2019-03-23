package com.lindj.boot.controller;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author lindj
 * @date 2019/3/21
 * @description
 */
@Component
@RefreshScope
@ConfigurationProperties(prefix = "lindj")
public class ConfigParams {

    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}