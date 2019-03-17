package com.lindj.boot.controller;

import com.lindj.boot.provider.ProviderClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: lindj
 * @date: 20:08
 * @desciption:
 **/
@RestController
@RequestMapping("/api/consumer")
public class ConsumerController {

    @Autowired
    private ProviderClient providerClient;

    @GetMapping(value = "/get")
    public Object getValue(){
        return providerClient.getValue();
    }


    @GetMapping(value = "/list")
    public String getList(){
        return "consumer list";
    }
}
