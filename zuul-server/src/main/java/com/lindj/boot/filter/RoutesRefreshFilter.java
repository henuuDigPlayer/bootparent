package com.lindj.boot.filter;

import com.lindj.boot.service.DynamicRouter;
import com.lindj.boot.service.RouteService;
import com.lindj.boot.service.ZuulRefreshService;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.RoutesRefreshedEvent;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lindj
 * @date 2019/3/25
 * @description
 */
@Component
public class RoutesRefreshFilter extends ZuulFilter {

  /*  @Autowired
    private ZuulRefreshService zuulRefreshService;*/
    @Autowired
    private RouteService routeService;

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String servletPath = request.getServletPath();
        System.out.println(servletPath);

        return null;
    }
}
