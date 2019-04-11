package com.lindj.boot.mapper;


import com.lindj.boot.model.SysConstant;

/**
 * @author: lindj
 * @date: 2019-02-26 18:13:13
 * @description:
 */
public interface SysConstantMapper {
    /**
     * 根据key查询信息
     *
     * @param name String
     * @return SysConstant
     */
    SysConstant selectByName(String name);
}