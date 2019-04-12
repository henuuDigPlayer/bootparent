package com.lindj.boot.filter;

import com.lindj.boot.bean.LogBean;
import com.lindj.boot.model.Route;
import com.lindj.boot.service.RouteService;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.zjdex.framework.util.constant.ConstantUtil;
import com.zjdex.framework.util.data.DateUtil;
import com.zjdex.framework.util.data.JsonUtil;
import com.zjdex.framework.util.data.StringUtil;
import com.zjdex.framework.util.http.HttpRequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Date;

/**
 * @author lindj
 * @date 2019/4/10 0010
 * @description
 */
@Component
public class LogFilter extends ZuulFilter {

    private static final Logger logger = LoggerFactory.getLogger(LogFilter.class);

    @Autowired
    private RouteService routeService;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return 9;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        try {
            String value = JsonUtil.objectToJson(getLogBean());
            kafkaTemplate.send("logs", value);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取日志对象
     *
     * @return
     */
    private LogBean getLogBean() throws IOException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String servletPath = request.getServletPath();
        servletPath = servletPath.substring(servletPath.indexOf("/") + 1, servletPath.length());
        servletPath = servletPath.substring(0, servletPath.indexOf("/"));
        Long beginTime = (Long) request.getAttribute("beginTime");
        Long complete = System.currentTimeMillis() - beginTime;

        String body = null;
        String contentType = requestContext.getResponse().getContentType();
        if(contentType.contains("json")){
            InputStream inputStream = requestContext.getResponseDataStream();
            body = StreamUtils.copyToString(inputStream, Charset.forName("UTF-8"));
            requestContext.setResponseBody(body);
        }


        Route route = this.routeService.getRouteByServiceId(servletPath);

        LogBean logBean = new LogBean();
        String method = request.getMethod();
        logBean.setServerName(servletPath);
        logBean.setSourceIp(HttpRequestUtil.getIpAddr(request));
        logBean.setRequestMethod(method);
        logBean.setComplete(complete);
        logBean.setApiUrl(request.getRequestURI());
        logBean.setLinkMan(route.getLinkMan());
        logBean.setTelephone(route.getTelphone());
        logBean.setEmail(route.getEmail());
        logBean.setTime(DateUtil.getDateString(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS));
        logBean.setContent(body);
        URL url = requestContext.getRouteHost();
        logBean.setPort(url.getPort());
        logBean.setRouteHostUrl(url.getHost());
        if (!StringUtil.isEmpty(request.getContentType()) &&
                !request.getContentType().contains(ConstantUtil.FILE_CONTENT_TYPE) && !method.equals(ConstantUtil.METHOD_GET)) {
            logBean.setArgs(HttpRequestUtil.getPostArgs(request));
        }

        return logBean;
    }
}
