package com.waterworks.service.userService.impl;

import com.github.pagehelper.PageHelper;
import com.waterworks.repository.mapper.UserMapper;
import com.waterworks.repository.entity.User;
import com.waterworks.service.tools.currentTime.CurrentDateTime;
import com.waterworks.service.userService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service

public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;//这里会报错，但是并不会影响

    @Override
    public int addUser(User user) {
        user.setRegisterTime(CurrentDateTime.currentTime());
        user.setRoleId(1);
        return userMapper.insertUser(user);
    }

    @Override
    public User findUser(int id) {

        return userMapper.findUserById(id);
    }

    @Override
    public String updateUser(User user) {
        user.setRegisterTime(CurrentDateTime.currentTime());
        user.setRoleId(3);
        userMapper.updateUserById(user);
        return null;
    }

    @Override
    public boolean deleteUser(int id) {
        return userMapper.deleteUserById(id);
    }

    /*
    * 这个方法中用到了我们开头配置依赖的分页插件pagehelper
    * 很简单，只需要在service层传入参数，然后将参数传递给一个插件的一个静态方法即可；
    * pageNum 开始页数
    * pageSize 每页显示的数据条数
    * */
    @Override
    public List<User> findAllUser(int pageNum, int pageSize) {
        //将参数传给这个方法就可以实现物理分页了，非常简单。
        PageHelper.startPage(pageNum, pageSize);
        return userMapper.selectAllUsers();
    }
}
