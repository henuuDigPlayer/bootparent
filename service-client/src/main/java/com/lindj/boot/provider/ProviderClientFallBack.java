package com.lindj.boot.provider;

import org.springframework.stereotype.Component;

/**
 * @author: lindj
 * @date: 20:45
 * @desciption:
 **/
@Component
public class ProviderClientFallBack implements ProviderClient {
    @Override
    public String getValue() {
        return "call remote method error";
    }
}
