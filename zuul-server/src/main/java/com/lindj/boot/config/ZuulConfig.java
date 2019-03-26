package com.lindj.boot.config;

import com.lindj.boot.filter.RoutesRefreshFilter;
import com.lindj.boot.service.DynamicRouter;
import com.lindj.boot.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cloud.netflix.zuul.filters.CompositeRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.cloud.netflix.zuul.filters.SimpleRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

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
    public RoutesRefreshFilter getRoutesRefreshFilter(){
        return new RoutesRefreshFilter();
    }
}
