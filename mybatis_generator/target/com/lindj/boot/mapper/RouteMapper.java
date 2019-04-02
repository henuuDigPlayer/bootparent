package com.lindj.boot.mapper;

import com.lindj.boot.model.Route;
import com.lindj.boot.model.RouteExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author: lindj
 * @date: 2019-03-29 18:43:01
 * @description:  
 */
public interface RouteMapper {
    /**
     * countByExample
     * 
     * @param example RouteExample 
     * @return long 
     */
    long countByExample(RouteExample example);

    /**
     * deleteByExample
     * 
     * @param example RouteExample 
     * @return int 
     */
    int deleteByExample(RouteExample example);

    /**
     * deleteByPrimaryKey
     * 
     * @param id Integer 
     * @return int 
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * insert
     * 
     * @param record Route 
     * @return int 
     */
    int insert(Route record);

    /**
     * insertSelective
     * 
     * @param record Route 
     * @return int 
     */
    int insertSelective(Route record);

    /**
     * selectByExample
     * 
     * @param example RouteExample 
     * @return List<Route> 
     */
    List<Route> selectByExample(RouteExample example);

    /**
     * selectByPrimaryKey
     * 
     * @param id Integer 
     * @return Route 
     */
    Route selectByPrimaryKey(Integer id);

    /**
     * updateByExampleSelective
     * 
     * @param record Route 
     * @param example RouteExample 
     * @return int 
     */
    int updateByExampleSelective(@Param("record") Route record, @Param("example") RouteExample example);

    /**
     * updateByExample
     * 
     * @param record Route 
     * @param example RouteExample 
     * @return int 
     */
    int updateByExample(@Param("record") Route record, @Param("example") RouteExample example);

    /**
     * updateByPrimaryKeySelective
     * 
     * @param record Route 
     * @return int 
     */
    int updateByPrimaryKeySelective(Route record);

    /**
     * updateByPrimaryKey
     * 
     * @param record Route 
     * @return int 
     */
    int updateByPrimaryKey(Route record);
}