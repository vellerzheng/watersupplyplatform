package com.main.waterworks.repository.mapper;

import com.main.waterworks.repository.entity.Users;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import java.util.List;

@Mapper
public interface UsersMapper {
    int deleteByPrimaryKey(Long id);

    @Insert("insert into user(username,password,email,phone,registerTime,roleId) " +
            " values(#{username},#{password},#{email},#{phone},#{registerTime},#{roleId}")
    @SelectKey(statement = "call identity()",keyProperty = "id",before = false,resultType = Integer.class)
    int insert(Users record);

    int insertSelective(Users record);

    Users selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Users record);

    int updateByPrimaryKey(Users record);

    @Select("select id,username,email,phone from user")
    List<Users> selectAllUsers();
}