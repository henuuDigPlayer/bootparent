package com.lindj.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * Hello world!
 *
 */
@EnableEurekaClient
@SpringBootApplication
@EnableZuulProxy
public class Application {
    public static void main( String[] args ) {
        SpringApplication.run(Application.class, args);
    }
}
