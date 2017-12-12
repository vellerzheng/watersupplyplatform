package com.waterworks.repository.mapper;

import com.waterworks.repository.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {

    int insertUser(User user);

    User findUserById(Integer id);

    int updateUserById(User user);

    boolean deleteUserById(Integer id);

 //   @Select("select id,username,email,phone from user")
    List<User> selectAllUsers();
}