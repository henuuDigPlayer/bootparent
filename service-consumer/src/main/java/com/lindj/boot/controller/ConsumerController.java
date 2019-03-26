package com.lindj.boot.controller;

import com.lindj.boot.provider.ProviderClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private Logger logger = LoggerFactory.getLogger(ConsumerController.class);

    @Autowired
    private ProviderClient providerClient;

    @GetMapping(value = "/get")
    public Object getValue(){
        return providerClient.getValue();
    }


    @GetMapping(value = "/list")
    public String getList(){
        for(long i = 0; i <= 10000000; i++){
            logger.info("infor execute {}", i);
            logger.warn("infor execute {}", i);
            logger.error("infor execute {}", i);
        }
        return "consumer list";
    }
}
