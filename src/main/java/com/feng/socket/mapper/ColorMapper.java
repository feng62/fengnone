package com.feng.socket.mapper;

import com.feng.socket.pojo.Color;

/**
* @author 33359
* @description 针对表【color】的数据库操作Mapper
* @createDate 2022-04-15 10:12:33
* @Entity com.feng.socket.pojo.Color
*/
public interface ColorMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Color record);

    int insertSelective(Color record);

    Color selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Color record);

    int updateByPrimaryKey(Color record);

}
