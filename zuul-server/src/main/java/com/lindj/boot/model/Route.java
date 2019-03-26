package com.lindj.boot.model;

import java.util.Date;

/**
 * @author: lindj
 * @date: 2019-03-25 15:50:49
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
    private boolean stripPrefix;

    /**
     * 是否支持重试
     */
    private Boolean retryable;

    /**
     * 
     */
    private boolean customSensitiveHeaders;

    /**
     * 
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
    public boolean getStripPrefix() {
        return stripPrefix;
    }

    /**
     * 路由前缀是否在转发开始前被删除 默认是删除
     *
     * @param stripPrefix 路由前缀是否在转发开始前被删除 默认是删除
     */
    public void setStripPrefix(boolean stripPrefix) {
        this.stripPrefix = stripPrefix;
    }

    /**
     * 是否支持重试
     *
     * @return retryable 是否支持重试
     */
    public Boolean getRetryable() {
        return retryable;
    }

    /**
     * 是否支持重试
     *
     * @param retryable 是否支持重试
     */
    public void setRetryable(Boolean retryable) {
        this.retryable = retryable;
    }

    /**
     * 
     *
     * @return custom_sensitive_headers 
     */
    public boolean getCustomSensitiveHeaders() {
        return customSensitiveHeaders;
    }

    /**
     * 
     *
     * @param customSensitiveHeaders 
     */
    public void setCustomSensitiveHeaders(boolean customSensitiveHeaders) {
        this.customSensitiveHeaders = customSensitiveHeaders;
    }

    /**
     * 
     *
     * @return status 
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 
     *
     * @param status 
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
}