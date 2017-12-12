package com.waterworks.service.userService;

import com.waterworks.repository.entity.User;

import java.util.List;

public interface UserService {
    int addUser(User user);

    User findUser(int id);

    String updateUser(User user);

    boolean deleteUser(int id);

    List<User> findAllUser(int pageNum, int pageSize);
}
