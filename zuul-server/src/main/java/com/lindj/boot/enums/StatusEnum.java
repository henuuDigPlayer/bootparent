package com.lindj.boot.enums;

/**
 * @author: lindj
 * @date: 2018/12/16
 * @description:
 */
public enum StatusEnum {
    /**
     * 正常
     */
    OK(1, "正常"),

    /**
     * 删除
     */
    DELETED(-1, "删除"),

    /**
     * 下架
     */
    LOWER(0, "下架");

    private Integer code;
    private String title;

    StatusEnum(Integer code, String title) {
        this.code = code;
        this.title = title;
    }

    public static boolean exists(Integer code){
        StatusEnum[] statusEnums = StatusEnum.values();
        for(StatusEnum statusEnum : statusEnums){
            if(statusEnum.getCode().equals(code)){
                return true;
            }
        }
        return false;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
