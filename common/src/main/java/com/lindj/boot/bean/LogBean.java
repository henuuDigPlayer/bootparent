package com.lindj.boot.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lindj
 * @date 2019/3/29
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogBean {

    private String serverName;
    private String sourceIp;
    private String requestMethod;
    private Long complete;
    private String apiUrl;
    private String linkMan;
    private String telephone;
    private String email;
    private String content;
    private String time;
    private String args;
    private Integer port;
    private String routeHostUrl;

    @Override
    public String toString() {
        return "{\n" +
                " \"服务名称\":\"" + serverName + "\",\n" +
                "\"来源ip\":\"" + sourceIp + "\",\n" +
                "\"请求方法\":\"" + requestMethod + "\",\n" +
                "\"完成时间\":" + complete  + ",\n" +
                "\"请求接口\":\"" + apiUrl + "\",\n" +
                "\"联系人\":\"" + linkMan + "\",\n" +
                "\"电话\":\"" + telephone + "\",\n" +
                "\"邮件\":\"" + email + "\",\n" +
                "\"请求时间\":\"" + time + "\",\n" +
                "\"请求参数\":\"" + args + "\",\n" +
                "\"请求端口\":" + port + ",\n" +
                "\"请求服务地址\":\"" + routeHostUrl + "\",\n" +
                '}';
    }
}
