package com.lindj.boot.model;

import java.util.Date;

/**
 * @author: lindj
 * @date: 2019-03-29 18:43:01
 * @description:  
 */
public class Route {
    /**
     * 路由的唯一编号
     */
    private Integer id;

    /**
     * 路由的规则 /foo/**.
     */
    private String path;

    /**
     * 服务实例ID
     */
    private String serviceId;

    /**
     * 
     */
    private String url;

    /**
     * 路由前缀是否在转发开始前被删除 默认是删除
     */
    private Integer stripPrefix;

    /**
     * 是否支持重试
     */
    private Integer retryable;

    /**
     * 
     */
    private Integer customSensitiveHeaders;

    /**
     * -1删除 1正常
     */
    private Integer status;

    /**
     * 
     */
    private String version;

    /**
     * 
     */
    private Date createTime;

    /**
     * 
     */
    private Date modifyTime;

    /**
     * 联系人
     */
    private String linkMan;

    /**
     * 电话
     */
    private String telphone;

    /**
     * 邮件
     */
    private String email;

    /**
     * 路由的唯一编号
     *
     * @return id 路由的唯一编号
     */
    public Integer getId() {
        return id;
    }

    /**
     * 路由的唯一编号
     *
     * @param id 路由的唯一编号
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 路由的规则 /foo/**.
     *
     * @return path 路由的规则 /foo/**.
     */
    public String getPath() {
        return path;
    }

    /**
     * 路由的规则 /foo/**.
     *
     * @param path 路由的规则 /foo/**.
     */
    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    /**
     * 服务实例ID
     *
     * @return service_id 服务实例ID
     */
    public String getServiceId() {
        return serviceId;
    }

    /**
     * 服务实例ID
     *
     * @param serviceId 服务实例ID
     */
    public void setServiceId(String serviceId) {
        this.serviceId = serviceId == null ? null : serviceId.trim();
    }

    /**
     * 
     *
     * @return url 
     */
    public String getUrl() {
        return url;
    }

    /**
     * 
     *
     * @param url 
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * 路由前缀是否在转发开始前被删除 默认是删除
     *
     * @return strip_prefix 路由前缀是否在转发开始前被删除 默认是删除
     */
    public Integer getStripPrefix() {
        return stripPrefix;
    }

    /**
     * 路由前缀是否在转发开始前被删除 默认是删除
     *
     * @param stripPrefix 路由前缀是否在转发开始前被删除 默认是删除
     */
    public void setStripPrefix(Integer stripPrefix) {
        this.stripPrefix = stripPrefix;
    }

    /**
     * 是否支持重试
     *
     * @return retryable 是否支持重试
     */
    public Integer getRetryable() {
        return retryable;
    }

    /**
     * 是否支持重试
     *
     * @param retryable 是否支持重试
     */
    public void setRetryable(Integer retryable) {
        this.retryable = retryable;
    }

    /**
     * 
     *
     * @return custom_sensitive_headers 
     */
    public Integer getCustomSensitiveHeaders() {
        return customSensitiveHeaders;
    }

    /**
     * 
     *
     * @param customSensitiveHeaders 
     */
    public void setCustomSensitiveHeaders(Integer customSensitiveHeaders) {
        this.customSensitiveHeaders = customSensitiveHeaders;
    }

    /**
     * -1删除 1正常
     *
     * @return status -1删除 1正常
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * -1删除 1正常
     *
     * @param status -1删除 1正常
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 
     *
     * @return version 
     */
    public String getVersion() {
        return version;
    }

    /**
     * 
     *
     * @param version 
     */
    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }

    /**
     * 
     *
     * @return create_time 
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     *
     * @param createTime 
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     *
     * @return modify_time 
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * 
     *
     * @param modifyTime 
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * 联系人
     *
     * @return link_man 联系人
     */
    public String getLinkMan() {
        return linkMan;
    }

    /**
     * 联系人
     *
     * @param linkMan 联系人
     */
    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan == null ? null : linkMan.trim();
    }

    /**
     * 电话
     *
     * @return telphone 电话
     */
    public String getTelphone() {
        return telphone;
    }

    /**
     * 电话
     *
     * @param telphone 电话
     */
    public void setTelphone(String telphone) {
        this.telphone = telphone == null ? null : telphone.trim();
    }

    /**
     * 邮件
     *
     * @return email 邮件
     */
    public String getEmail() {
        return email;
    }

    /**
     * 邮件
     *
     * @param email 邮件
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }
}