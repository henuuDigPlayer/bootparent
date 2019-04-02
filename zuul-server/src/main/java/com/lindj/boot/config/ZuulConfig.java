package com.lindj.boot.config;

import com.lindj.boot.filter.RequestFilter;
import com.lindj.boot.service.DynamicRouter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lindj
 * @date 2019/3/25
 * @description
 */
@Configuration
public class ZuulConfig {
    @Autowired
    ZuulProperties zuulProperties;
    @Autowired
    ServerProperties server;

    @Bean
    public DynamicRouter propertiesRouter() {
        return new DynamicRouter(this.server.getServlet().getServletPrefix(), this.zuulProperties);
    }
    @Bean
    public RequestFilter getRoutesRefreshFilter(){
        return new RequestFilter();
    }
}
