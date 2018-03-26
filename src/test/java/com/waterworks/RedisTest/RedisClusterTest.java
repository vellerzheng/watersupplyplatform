package com.waterworks.RedisTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ConfigurationProperties(prefix = "spring.redis.cluster")
public class RedisClusterTest {

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Test
    public void redisTest() {
        String key = "redisTestKey";
        String value = "I am test value";

        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();

        //数据插入测试：
        opsForValue.set(key, value);
        String valueFromRedis = opsForValue.get(key);
        System.out.println("redis value after set: {}" + valueFromRedis);
        // assertThat(valueFromRedis, is(value));

        //数据删除测试：
        redisTemplate.delete(key);
        valueFromRedis = opsForValue.get(key);
        System.out.println("redis value after delete: {}" + valueFromRedis);
        // assertThat(valueFromRedis, equalTo(null));
    }
}
