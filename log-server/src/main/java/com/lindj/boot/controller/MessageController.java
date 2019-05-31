package com.lindj.boot.controller;

import com.lindj.boot.bean.MessageEntity;
import com.lindj.boot.manager.MyKafkaProducer;
import com.lindj.boot.model.OrderInfo;
import com.zjdex.framework.util.data.JsonUtil;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

/**
 * @author lindj
 * @date 2019/4/11 0011
 * @description
 */
@RestController
@RequestMapping("/api/order")
public class MessageController {

    private static Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private MyKafkaProducer myKafkaProducer;

    @GetMapping(value = "/get")
    public Object getConstant(){
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setName("test");
        orderInfo.setId(1);
        MessageEntity messageEntity =
                MessageEntity.builder().data(orderInfo).code(System.currentTimeMillis() + "").title("lindj").build();
        this.myKafkaProducer.send("test", System.currentTimeMillis() + "", messageEntity);
        return null;
    }

}
