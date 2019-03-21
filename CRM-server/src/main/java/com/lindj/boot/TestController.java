package com.lindj.boot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lindj
 * @date 2019/3/21
 * @description
 */
@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    private ConfigParams configParams;


    @GetMapping("/gets")
    public Integer getValue(){
        return this.configParams.getPort();
    }
}
