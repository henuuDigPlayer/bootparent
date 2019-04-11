package com.lindj.boot.manager;

import com.lindj.boot.config.SysConstantConfig;
import com.zjdex.framework.bean.BaseResponse;
import com.zjdex.framework.exception.CodeException;
import com.zjdex.framework.util.ResultCode;
import com.zjdex.framework.util.data.JsonUtil;
import com.zjdex.framework.util.http.HttpRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MessageManager {

    @Autowired
    private SysConstantConfig sysConstantConfig;

    /**
     * 发送验证码
     *
     * @param phone String
     */
    public void sendMessage(String phone, String content) {
        Map<String, Object> paramsMap = new HashMap<>(2);
        paramsMap.put("phone", phone);
        paramsMap.put("content", content);
        paramsMap.put("appId", "HY");
        String value = HttpRequestUtil.doPost(sysConstantConfig.getValue("messageUrl"), JsonUtil.objectToJson(paramsMap), null);
        BaseResponse baseResp = JsonUtil.parseToObject(value, BaseResponse.class);
        if (baseResp != null && !baseResp.getCode().equals(ResultCode.Codes.SUCCESS.getCode())) {
            throw new CodeException(baseResp.getCode(), baseResp.getMessage());
        }
    }

}
