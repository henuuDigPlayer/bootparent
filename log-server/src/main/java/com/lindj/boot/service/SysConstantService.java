package com.lindj.boot.service;

import com.lindj.boot.mapper.SysConstantMapper;
import com.lindj.boot.model.SysConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author lindj
 * @date 2019/2/26
 * @description
 */
@Service
public class SysConstantService {


    @Autowired
    private SysConstantMapper sysConstantMapper;

    /**
     * 根据名称获取配置信息
     *
     * @return SysConstant
     */
    public SysConstant getContent(String name) {
        return this.sysConstantMapper.selectByName(name);
    }

}