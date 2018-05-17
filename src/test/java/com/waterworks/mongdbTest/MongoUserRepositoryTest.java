package com.waterworks.mongdbTest;

import com.waterworks.repository.entity.MongoUser;
import com.waterworks.repository.mapper.MsgInfoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoUserRepositoryTest {
    private Logger logger =  LoggerFactory.getLogger(this.getClass());
    @Autowired
    MsgInfoRepository msgInfoRepository;

    @Test
    public void add(){
        MongoUser mongoUser=new MongoUser();
        String id= UUID.randomUUID().toString();
        mongoUser.setId(id);
        mongoUser.setUserId(112222);
        mongoUser.setUserName("江海通");
        msgInfoRepository.save(mongoUser);

        MongoUser mongoUser1 =new MongoUser();
        mongoUser1 = msgInfoRepository.findOne(id);
        List<MongoUser> list = msgInfoRepository.findAll();
        for(MongoUser mgUser : list)
            System.out.println("All user is :" + mgUser);
        msgInfoRepository.deleteAll();
        List<MongoUser> list1 = msgInfoRepository.findAll();

    }


}
