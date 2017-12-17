package com.waterworks.RedisTest;


import com.waterworks.util.redis.RedisClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;


@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisServiceTest {

    @Autowired
    private RedisClient redisClient;

    @Test
    public void redisSetTest() {
        String info = "hello redis!";

        redisClient.set("test",info, (long)10);  //10秒过期

    }

   @Test
    public void redisGetTest() {

        Object message = redisClient.get("test");
        System.out.println(message.toString());

    }
}
