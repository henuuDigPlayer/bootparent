package com.lindj.boot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.RefreshableRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.SimpleRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author lindj
 * @date 2019/3/25
 * @description
 */
public class DynamicRouter extends SimpleRouteLocator implements RefreshableRouteLocator {

    @Autowired
    private RouteService routeService;

    public DynamicRouter(String servletPath, ZuulProperties properties) {
        super(servletPath, properties);
    }

    @Override
    protected Map<String, ZuulProperties.ZuulRoute> locateRoutes() {
        return routeService.getRoutes();
    }

    @Override
    public void refresh() {
        doRefresh();
    }
}
