package com.waterworks.RedisTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.JedisCluster;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisClusterTest2 {
    @Autowired
    private JedisCluster jedisCluster;

    @Test
    public void set(){
        jedisCluster.set("hello","word");
    }

    @Test
    public void get(){
        System.out.println("=============="+jedisCluster.get("hello"));
    }
}
