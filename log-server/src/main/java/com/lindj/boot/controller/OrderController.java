package com.lindj.boot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lindj.boot.config.SysConstantConfig;
import com.lindj.boot.mapper.OrderInfoMapper;
import com.lindj.boot.model.OrderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lindj
 * @date 2019/4/11 0011
 * @description
 */
@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderInfoMapper orderInfoMapper;
    @Autowired
    private SysConstantConfig sysConstantConfig;

    @GetMapping(value = "/get")
    public Object getConstant(){

       /* QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<OrderInfo>();
        queryWrapper.between("create_time", "2019-03-10", "2019-09-15");
        Page<OrderInfo> page = new Page<OrderInfo>();
        page.setCurrent(2);
        IPage<OrderInfo> pages = this.orderInfoMapper.selectPage(page, queryWrapper);*/
        return sysConstantConfig.getValue("requestTimeOut");

    }
}
