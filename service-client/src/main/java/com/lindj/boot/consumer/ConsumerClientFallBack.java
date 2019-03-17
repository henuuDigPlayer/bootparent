package com.lindj.boot.consumer;

import com.lindj.boot.provider.ProviderClient;
import org.springframework.stereotype.Component;

/**
 * @author: lindj
 * @date: 20:45
 * @desciption:
 **/
@Component
public class ConsumerClientFallBack implements ConsumerClient {
    @Override
    public String getList() {
        return "call remote method error";
    }
}
