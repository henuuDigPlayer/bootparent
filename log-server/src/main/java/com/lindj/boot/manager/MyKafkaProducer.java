package com.lindj.boot.manager;

import com.zjdex.framework.util.data.JsonUtil;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * @author lindj
 * @date 2019/5/27 0027
 * @description
 */
@Component
public class MyKafkaProducer {

    private static Logger logger = LoggerFactory.getLogger(MyKafkaProducer.class);

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void send(String topic, String key, Object entry){
        ProducerRecord<String, Object> record = new ProducerRecord<String, Object>(topic, key,
                JsonUtil.objectToJson(entry));

        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(record);
        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onFailure(Throwable throwable) {
                logger.error("{}" , throwable);
            }
            @Override
            public void onSuccess(SendResult<String, Object> sendResult) {
                RecordMetadata recordMetadata = sendResult.getRecordMetadata();
                logger.info("{},{}, {}, {}", recordMetadata.partition() , recordMetadata.offset() ,
                        recordMetadata.topic(), recordMetadata.hasOffset());
            }
        });
    }
}
