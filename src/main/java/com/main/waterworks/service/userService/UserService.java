package com.main.waterworks.service.userService;

import com.main.waterworks.dao.model.Users;

import java.util.List;

public interface UserService {
    int addUser(Users user);


    List<Users> findAllUser(int pageNum, int pageSize);
}
