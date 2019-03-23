package com.lindj.boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: lindj
 * @date: 20:02
 * @desciption:
 **/
@RestController
@RequestMapping("/api/provider")
public class ProviderController {

    @Autowired
    private ConfigParams configParams;


    @GetMapping(value = "/set")
    public Object get(){
        return  this.configParams.getId();
    }
}
