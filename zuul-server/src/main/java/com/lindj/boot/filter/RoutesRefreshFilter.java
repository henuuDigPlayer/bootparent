package com.lindj.boot.filter;

import com.lindj.boot.service.DynamicRouter;
import com.lindj.boot.service.RouteService;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.RoutesRefreshedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lindj
 * @date 2019/3/25
 * @description
 */
//@Component
public class RoutesRefreshFilter extends ZuulFilter {

    @Autowired
    DynamicRouter routeLocator;

    @Autowired
    ApplicationEventPublisher publisher;

    @Autowired
    private RouteService routeService;

    @Override
    public String filterType() {
        return "pre";
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
        servletPath = servletPath.substring(servletPath.indexOf("/") + 1, servletPath.length());
        if(servletPath.contains("/")){
            servletPath = servletPath.substring(0, servletPath.indexOf("/"));
        }
        String value = RouteService.SingletonEnum.INSTANCE.getValue(servletPath);
        System.out.println(request.getServletPath());
       /* if(StringUtils.isEmpty(value)){
            publisher.publishEvent(new RoutesRefreshedEvent(routeLocator));
        }*/
        return null;
    }
}
