package com.lindj.boot.service;

import com.lindj.boot.enums.StatusEnum;
import com.lindj.boot.mapper.RouteMapper;
import com.lindj.boot.model.Route;
import com.lindj.boot.model.RouteExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author lindj
 * @date 2019/3/25
 * @description
 */
@Service
public class RouteService {

    private static Logger logger = LoggerFactory.getLogger(RouteService.class);

    @Autowired
    private RouteMapper routeMapper;

    public Map<String, ZuulProperties.ZuulRoute> getRoutes() {
        RouteExample example = new RouteExample();
        example.createCriteria().andStatusEqualTo(StatusEnum.OK.getCode());
        List<Route> results = this.routeMapper.selectByExample(example);

        System.out.println("获取数据");
        LinkedHashMap<String, ZuulProperties.ZuulRoute> routes = new LinkedHashMap<String, ZuulProperties.ZuulRoute>();
        ZuulProperties.ZuulRoute zuulRoute = null;
        for (Route result : results) {
            if (StringUtils.isEmpty(result.getPath())) {
                continue;
            }
            zuulRoute = new ZuulProperties.ZuulRoute();
            try {
                BeanUtils.copyProperties(result, zuulRoute);
            } catch (Exception e) {
            }
            zuulRoute.setId(result.getId().toString());
            routes.put(zuulRoute.getPath(), zuulRoute);
        }
        return routes;
    }
}
