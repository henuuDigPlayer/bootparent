package com.lindj.boot;

import com.lindj.boot.service.DynamicRouter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.RoutesRefreshedEvent;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.ApplicationEventPublisher;

/**
 * Hello world!
 *
 */
//@EnableEurekaClient
@MapperScan("com.lindj.boot.mapper")
@SpringBootApplication
@EnableZuulProxy
public class Application {

    /*@Autowired
    DynamicRouter routeLocator;

    @Autowired
    ApplicationEventPublisher publisher;*/

    public static void main( String[] args ) {

        SpringApplication.run(Application.class, args);
    }

  /*  @Override
    public void run(String... args) throws Exception {
        System.out.println("加载信息");
        publisher.publishEvent(new RoutesRefreshedEvent(routeLocator));
    }*/
}
