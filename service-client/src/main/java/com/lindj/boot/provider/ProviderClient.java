package com.lindj.boot.provider;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author: lindj
 * @date: 20:17
 * @desciption:
 **/
@FeignClient(value = "provider-service", fallback = ProviderClientFallBack.class)
public interface ProviderClient {
    @GetMapping(value = "/api/provider/get")
    String getValue();
}
