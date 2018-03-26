package com.waterworks.util.redis;


import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;
import java.util.*;


@Configuration
@ConditionalOnClass(RedisClusterConfig.class)
@EnableConfigurationProperties(RedisClusterProperties.class)
public class RedisClusterConfig {

    @Resource
    private RedisClusterProperties redisClusterProperties;

    @Bean
    public JedisCluster redisCluster(){

        Set<HostAndPort> nodes = new HashSet<>();
        for (String node:redisClusterProperties.getNodes()){
            String[] parts= StringUtils.split(node,":");
            Assert.state(parts.length==2, "redis node shoule be defined as 'host:port', not '" + Arrays.toString(parts) + "'");
            nodes.add(new HostAndPort(parts[0], Integer.valueOf(parts[1])));
        }

        return new JedisCluster(nodes);
    }


}