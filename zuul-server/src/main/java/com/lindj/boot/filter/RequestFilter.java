package com.lindj.boot.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lindj
 * @date 2019/3/25
 * @description
 */
@Component
public class RequestFilter extends ZuulFilter {

    private static final Logger logger = LoggerFactory.getLogger(LogFilter.class);
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
        logger.info(servletPath);
        request.setAttribute("beginTime", System.currentTimeMillis());


        return null;
    }
}
