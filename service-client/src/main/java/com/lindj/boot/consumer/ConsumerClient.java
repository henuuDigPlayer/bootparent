package com.lindj.boot.consumer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author: lindj
 * @date: 22:06
 * @desciption:
 **/
@FeignClient(value = "consumer-service", fallback = ConsumerClientFallBack.class)
public interface ConsumerClient {
    @GetMapping(value = "/api/consumer/list")
    String getList();
}
