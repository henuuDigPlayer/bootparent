package com.lindj.boot.service;

import com.lindj.boot.bean.LogBean;
import com.lindj.boot.config.SysConstantConfig;
import com.lindj.boot.manager.MessageManager;
import com.zjdex.framework.bean.BaseResponse;
import com.zjdex.framework.util.PropertyUtil;
import com.zjdex.framework.util.ResultCode;
import com.zjdex.framework.util.data.JsonUtil;
import com.zjdex.framework.util.data.StringUtil;
import io.socket.client.IO;
import io.socket.client.Socket;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.net.URISyntaxException;
import java.util.Optional;

/**
 * @author lindj
 * @date 2019/4/10 0010
 * @description
 */
@Service
public class LoggerConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(LoggerConsumerService.class);

    private Socket socket;

    @Autowired
    private MessageManager messageManager;
    @Autowired
    private SysConstantConfig sysConstantConfig;

    @KafkaListener(topics = {"logs"})
    public void listen(ConsumerRecord<String, String> record) {
        Optional<String> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            LogBean logBean = JsonUtil.parseToObject(kafkaMessage.get(), LogBean.class);
            String body =  logBean.getContent();
            Long timeout = Long.parseLong(sysConstantConfig.getValue("requestTimeOut"));
            String content = null;
            logger.info("日志信息: {}", JsonUtil.objectToJson(logBean));
            if(!StringUtil.isEmpty(body)) {


                BaseResponse response = JsonUtil.parseToObject(body, BaseResponse.class);
                if (!response.getCode().equals(ResultCode.Codes.SUCCESS.getCode())) {
                    content = PropertyUtil.getProperty("requestError", timeout.toString()) + logBean.toString();
                    messageManager.sendMessage(logBean.getTelephone(), content);
                }
            }
            if (logBean.getComplete() > timeout) {
                content = PropertyUtil.getProperty("requestTimeOutError", timeout.toString()) + logBean.toString();
                messageManager.sendMessage(logBean.getTelephone(), content);
            }
//            socket.emit("chat message", message);
        }

    }

    @PostConstruct
    private void getPushConnection() throws URISyntaxException {
        socket = IO.socket("http://192.168.1.152:3000");
        socket.connect();
    }
}
