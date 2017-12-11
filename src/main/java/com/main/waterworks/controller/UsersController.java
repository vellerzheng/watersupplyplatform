package com.main.waterworks.controller;

import com.main.waterworks.dao.model.Users;
import com.main.waterworks.service.userService.UserService;
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
    public int addUser(Users user){
        return userService.addUser(user);
    }


    @RequestMapping(value = "/all/{pageNum}/{pageSize}",  method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    public Object findAllUser(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize){

        return userService.findAllUser(pageNum,pageSize);
    }

}
