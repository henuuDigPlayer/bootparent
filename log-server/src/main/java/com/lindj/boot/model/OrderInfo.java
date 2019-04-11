package com.lindj.boot.model;

import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

import java.util.Date;

/**
 * @author: lindj
 * @date: 2019-04-11 11:20:32
 * @description:  
 */
@Data
public class OrderInfo {
    /**
     * 
     */
    private Integer id;

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    @Version
    private Integer version;

    private Date createTime;


}