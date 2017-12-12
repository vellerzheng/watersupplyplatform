package com.waterworks.userTest;

import com.waterworks.repository.mapper.UserMapper;
import com.waterworks.repository.entity.User;
import com.waterworks.service.tools.currentTime.CurrentDateTime;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class UserTest {
    @Autowired
    private UserMapper usersMapper;
    @Test
    public  void userTest(){
        User user =new User();
        user.setId(3);
        user.setUsername("kraft");
        user.setPassword("123456");
        user.setEmail("32467845@qq.com");
        user.setPhone("15476187526");
        user.setRegisterTime(CurrentDateTime.currentTime());
        user.setRoleId(1);

        usersMapper.updateUserById(user);

        User userfind =new User();
        userfind = usersMapper.findUserById(1);
  //      usersMapper.insertUser(user);
        List<User> lists= new ArrayList<>();
        lists=usersMapper.selectAllUsers();
        System.out.println(lists.get(0).getUsername());
        log.info(lists.get(0).getUsername());

    }
}
