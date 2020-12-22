package com.sgq.dao;

import com.sgq.pojo.Person;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Author : sgq
 * Date : 2020/12/9 16:09
 */
@Mapper
public interface PersonMapper {
    Person selectAll();

    @Select("select * from person where  name like '%${name}%'")
    Person selectByName(@Param("name") String name);
}
