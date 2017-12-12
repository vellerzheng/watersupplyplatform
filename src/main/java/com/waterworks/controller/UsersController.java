package com.waterworks.controller;

import com.waterworks.repository.entity.User;
import com.waterworks.service.userService.UserService;
import groovy.util.logging.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
public class UsersController {
    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public int addUser(User user){
        return userService.addUser(user);
    }

    @RequestMapping(value = "/findUser", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    public User findUser(int id){
        return userService.findUser(id);
    }

    @ResponseBody
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public String updateUser(User user){
        return userService.updateUser(user);
    }

    @ResponseBody
    @RequestMapping(value = "/deleteUser", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public boolean deleteUser(int id){
        return userService.deleteUser(id);
    }

    @RequestMapping(value = "/all/{pageNum}/{pageSize}",  method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    public Object findAllUser(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize){
        return userService.findAllUser(pageNum,pageSize);
    }

}
