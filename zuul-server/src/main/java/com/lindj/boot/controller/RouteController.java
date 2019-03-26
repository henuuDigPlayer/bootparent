package com.lindj.boot.controller;

import com.lindj.boot.service.RouteService;
import com.lindj.boot.service.ZuulRefreshService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lindj
 * @date 2019/3/25
 * @description
 */
@RestController
@RequestMapping("/api/route")
public class RouteController {

    @Autowired
    private RouteService routeService;

    @Autowired
    private ZuulRefreshService zuulRefreshService;


    @GetMapping(value = "/list", name = "")
    public Object getList(){

        this.zuulRefreshService.refreshRoute();
        return this.routeService.getRoutes();
    }
}
