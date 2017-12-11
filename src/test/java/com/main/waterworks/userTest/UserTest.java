package com.main.waterworks.userTest;

import com.main.waterworks.repository.mapper.UsersMapper;
import com.main.waterworks.repository.entity.Users;
import com.main.waterworks.service.tools.currentTime.CurrentDateTime;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
//@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class UserTest {
    @Autowired
    private UsersMapper usersMapper;
    @Test
    public  void userTest(){
        Users user =new Users();
        user.setUsername("vellerzheng");
        user.setPassword("123456");
        user.setEmail("vellerzheng2012@163.com");
        user.setPhone("13956127856");
        user.setRegisterTime(CurrentDateTime.currentTime());
        user.setRoleId(1);

   //     usersMapper.selectByPrimaryKey((long)1);
//        usersMapper.insert(user);
        List<Users> lists= new ArrayList<>();
        lists=usersMapper.selectAllUsers();
        System.out.println(lists.get(0).getUsername());
        log.info(lists.get(0).getUsername());
    }
}
