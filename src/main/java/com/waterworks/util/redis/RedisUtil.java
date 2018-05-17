package com.waterworks.util.redis;


import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.DefaultSortParameters;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.connection.SortParameters;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;


/**
 *
 * 基于spring和redis的redisTemplate工具类
 * 针对所有的hash 都是以h开头的方法
 * 针对所有的Set 都是以s开头的方法                    不含通用方法
 * 针对所有的List 都是以l开头的方法
 */
@Component
public class RedisUtil {


    @Resource
    private RedisTemplate<String, Object> template;

    private static RedisSerializer<Object> valueRedisSerializer = new JdkSerializationRedisSerializer();
    private static StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();


    public Boolean set(String key, Object value) {
        return template.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {

                connection.set(stringRedisSerializer.serialize(key), valueRedisSerializer.serialize(value));
                return true;
            }
        });
    }

    /**
     * 创建对象过期时间
     *
     * @param key
     * @param time  过期时间
     * @param value
     * @return
     */
    public Boolean setEx(final String key, final long time, final Object value) {
        return template.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {

                connection.setEx(stringRedisSerializer.serialize(key), time, valueRedisSerializer.serialize(value));
                return true;
            }
        });
    }

    /**
     * 获取key值对应的value，如果出现异常返回null
     *
     * @param key
     * @return
     */
    public Object get(final String key) {
        return template.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                return valueRedisSerializer.deserialize(connection.get(stringRedisSerializer.serialize(key)));
            }
        });
    }

    /**
     * 设置新值，并返回旧值
     * @param key
     * @param value
     * @return
     */
    public Object getSet(final String key,final Object value) {
        return template.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                return valueRedisSerializer.deserialize(connection.getSet(stringRedisSerializer.serialize(key),valueRedisSerializer.serialize(value)));
            }
        });
    }

    /**
     * 修改对应的key 名
     * @param oldkey
     * @param newKey
     */
    public Boolean rename(final String oldkey, final String newKey) {
        return template.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                connection.rename(stringRedisSerializer.serialize(oldkey),stringRedisSerializer.serialize(newKey));
                return  true;
            }
        });
    }

    /**
     * 删除对应的key值,并返回删除成功的key的数量，如果出现异常则返回null
     * @param key
     * @return
     */
    public Long del(final String key) {
        return template.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.del(stringRedisSerializer.serialize(key));
            }
        });
    }

    public Long release(final String key,final Object value){
        return template.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
                return connection.eval(stringRedisSerializer.serialize(script), ReturnType.INTEGER, 1,stringRedisSerializer.serialize(key),valueRedisSerializer.serialize(value));
            }
        });
    }

    /**
     * 获取key对应的key 值，并删除key，保证操作的一致性，返回获取的key值
     */
    public Object getAndDel(final String key) {
        return template.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                Object obj= valueRedisSerializer.deserialize(connection.get(stringRedisSerializer.serialize(key)));
                connection.del(stringRedisSerializer.serialize(key));
                return obj;
            }
        });
    }

    /**
     * 设置key值的超时时间，设置成功则返回true，失败则返回false。
     * @param key
     * @param seconds
     * @return
     */
    public Boolean expire(final String key, final Long seconds) {
        return template.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.expire(stringRedisSerializer.serialize(key), seconds);
            }
        });
    }

    /**
     * 设置key的超时时间，单位为毫秒
     * @param key
     * @param milliseconds
     * @return
     */
    public Boolean pExpire(final String key, final Long milliseconds) {
        return template.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.pExpire(stringRedisSerializer.serialize(key), milliseconds);
            }
        });
    }

    /**
     * 存储hash结构
     * @param key
     * @param field
     * @param value
     * @return
     */
    public Boolean hSet(final String key, final Object field, final Object value) {
        return template.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.hSet(stringRedisSerializer.serialize(key), valueRedisSerializer.serialize(field),valueRedisSerializer.serialize(value));
            }
        });
    }

    /**
     * 根据key值及字段值获取对应的value值。如不存在返回null。
     * @param key
     * @param field
     * @return
     */
    public Object hGet(final String key, final Object field) {
        return template.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] value = connection.hGet(stringRedisSerializer.serialize(key), valueRedisSerializer.serialize(field));
                return valueRedisSerializer.deserialize(value);
            }
        });
    }

    /**
     * 向set集合中添加元素
     * @param key
     * @param value
     * @return
     */
    public Boolean sAdd(final String key, final Object value){
        return template.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                connection.sAdd(stringRedisSerializer.serialize(key), valueRedisSerializer.serialize(value));
                return true;
            }
        });
    }

    /**
     * 查询set集合中的所有值
     * @param key
     * @return
     */
    public Set<Object> sMembers(final String key){
        return template.execute(new RedisCallback<Set<Object>>() {
            @Override
            public Set<Object> doInRedis(RedisConnection connection) throws DataAccessException {

                Set<byte[]> set = connection.sMembers(stringRedisSerializer.serialize(key));
                Set<Object> returnSet = new HashSet<>();
                for (byte[] bytes : set) {
                    returnSet.add(valueRedisSerializer.deserialize(bytes));
                }
                return returnSet;
            }
        });
    }

    public Boolean sisMember(final String key,final Object value){
        return template.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.sIsMember(stringRedisSerializer.serialize(key), valueRedisSerializer.serialize(value));
            }
        });
    }

    /**
     * 删除某个hash结构的某个字段
     * @param key
     * @param field
     */
    public Boolean hDel(final String key, final Object field) {
        return template.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                connection.hDel(stringRedisSerializer.serialize(key), valueRedisSerializer.serialize(field));
                return true;
            }
        });
    }

    /**
     * 获取hash结构key对应的所有field和value。
     * @param key
     * @return
     */
    public Map<Object,Object> hGetAll(final String key) {
        return template.execute(new RedisCallback<Map<Object,Object>>() {
            @Override
            public Map<Object,Object> doInRedis(RedisConnection connection) throws DataAccessException {
                Map<byte[], byte[]> all = connection.hGetAll(stringRedisSerializer.serialize(key));
                if(all.size() != 0)
                    return mapTransger2(all);
                return null;
            }
        });
    }

    /**
     * 批量存储hash结构
     * @param key
     * @param valueMap
     */
    public Boolean hMSet(final String key, final Map<Object,Object> valueMap) {
        return template.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                Map<byte[], byte[]> map = mapTransger1(valueMap);
                connection.hMSet(stringRedisSerializer.serialize(key), map);
                return true;
            }
        });
    }

    /**
     * 根据字段名称获取某个用户的部分信息
     * @param key
     * @param fields
     * @return
     */
    public List<Object> hMGet(final String key,final Object[] fields) {
        return template.execute(new RedisCallback<List<Object>>() {
            @Override
            public List<Object> doInRedis(RedisConnection connection) throws DataAccessException {
                byte[][] f = new byte[fields.length][];
                for(int i=0;i<fields.length;i++) {
                    f[i] = valueRedisSerializer.serialize(fields[i]);
                }
                List<byte[]> result = connection.hMGet(stringRedisSerializer.serialize(key), f);
                List<Object> list = new ArrayList<Object>();
                for (byte[] b : result) {
                    list.add(valueRedisSerializer.deserialize(b));
                }
                return list;
            }
        });
    }

    /**
     * 对list集合中的数据排序
     * @param key
     * @param sortParameters
     * @return
     */
    public List<Object> sort(final String key,final SortParameters sortParameters) {
        SortParameters sp = new DefaultSortParameters();
        return template.execute(new RedisCallback<List<Object>>() {
            @Override
            public List<Object> doInRedis(RedisConnection connection) throws DataAccessException {
                List<byte[]> bytes = connection.sort(stringRedisSerializer.serialize(key), sortParameters);
                if(bytes.size() != 0) {
                    List<Object> list = new ArrayList<Object>();
                    for (byte[] b:bytes) {
                        list.add(valueRedisSerializer.deserialize(b));
                    }
                    return list;
                }
                return null;
            }
        });
    }

    /**
     * 删除set集合中的部分数据
     * @param key
     * @param values
     * @return
     */
    public Long sRem(final String key,final Object[] values) {
        return template.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                int l = values.length;
                byte[][] v = new byte[l][];
                if(values.length != 0) {
                    for (int i = 0; i < l; i++) {
                        v[i] = valueRedisSerializer.serialize(values[i]);
                    }
                }
                return connection.sRem(stringRedisSerializer.serialize(key),v);
            }
        });
    }

    /**
     * 模糊查询key
     * @param key
     * @return
     */
    public Set<Object> keys(final String key) {
        return template.execute(new RedisCallback<Set<Object>>() {
            @Override
            public Set<Object> doInRedis(RedisConnection connection) throws DataAccessException {
                Set<byte[]> keys = connection.keys(stringRedisSerializer.serialize(key));
                if(keys.size() != 0) {
                    Set<Object> sets = new HashSet<Object>();
                    for (byte[] bs : keys) {
                        sets.add(stringRedisSerializer.deserialize(bs));
                    }
                    return sets;
                } else {
                    return null;
                }
            }
        });
    }

    /**
     * 判断key是否存在
     * @param key
     * @return
     */
    public Boolean exists(final String key) {
        return template.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.exists(stringRedisSerializer.serialize(key));
            }
        });
    }

    /**
     * 自增方法
     * @param key
     * @return
     */
    public Long incr(final String key) {
        return template.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.incr(stringRedisSerializer.serialize(key));
            }
        });
    }

    /**
     * 增加指定值
     * @param key
     * @param value
     * @return
     */
    public Long incrBy(final String key,final long value) {
        return template.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.incrBy(stringRedisSerializer.serialize(key),value);
            }
        });
    }

    /**
     * 增加指定值
     * @param key
     * @param value
     * @return
     */
    public Double incrBy(final String key,final double value) {
        return template.execute(new RedisCallback<Double>() {
            @Override
            public Double doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.incrBy(stringRedisSerializer.serialize(key),value);
            }
        });
    }

    /**
     * hash结构字段增加指定值
     * @param key
     * @param field
     * @param value
     * @return
     */
    public Long hIncrBy(final String key, final Object field, final long value) {
        return template.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.hIncrBy(stringRedisSerializer.serialize(key),valueRedisSerializer.serialize(field),value);
            }
        });
    }

    /**
     * hash结构字段增加指定值
     * @param key
     * @param field
     * @param value
     * @return
     */
    public Double hIncrBy(final String key, final Object field, final double value) {
        return template.execute(new RedisCallback<Double>() {
            @Override
            public Double doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.hIncrBy(stringRedisSerializer.serialize(key),valueRedisSerializer.serialize(field),value);
            }
        });
    }

    /**
     * 向列表尾部添加数据
     * @param key
     * @param value
     * @return
     */
    public Long rPush(final String key, final Object value) {
        return template.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.rPush(stringRedisSerializer.serialize(key),valueRedisSerializer.serialize(value));
            }
        });
    }

    /**
     * 从列表头部弹出元素
     * @param key
     * @return
     */
    public Object lPop(final String key) {
        return template.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                return valueRedisSerializer.deserialize(connection.lPop(stringRedisSerializer.serialize(key)));
            }
        });
    }

    /**
     * 从列表尾部弹出元素
     * @param key
     * @return
     */
    public Object rPop(final String key) {
        return template.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                return valueRedisSerializer.deserialize(connection.rPop(stringRedisSerializer.serialize(key)));
            }
        });
    }

    /**
     * 如果不存在则设置并返回true，否则返回false
     * @param key
     * @param value
     * @return
     */
    public Boolean setnx(final String key,final Object value) {
        return template.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.setNX(stringRedisSerializer.serialize(key), valueRedisSerializer.serialize(value));
            }
        });
    }

    /**
     * 获取redis时间戳
     * @return
     */
    public Long time() {
        return template.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.time();
            }
        });
    }

    /**
     * 有序集合set，添加数据方法
     * @param key
     * @param score
     * @param value
     * @return
     */
    public Boolean zAdd(final String key, final double score, final Object value) {
        return template.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.zAdd(stringRedisSerializer.serialize(key), score, valueRedisSerializer.serialize(value));
            }
        });
    }

    /**
     * 查询有序集合中的元素的个数
     * @param key
     * @return
     */
    public Long zCard(final String key) {
        return template.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.zCard(stringRedisSerializer.serialize(key));
            }
        });
    }

    /**
     * 删除有序集合中的元素
     * @param key
     * @param values
     * @return
     */
    public Long zRem(final String key,final Object[] values) {
        return template.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                int l = values.length;
                byte[][] v = new byte[l][];
                if(values.length != 0) {
                    for (int i = 0; i < l; i++) {
                        v[i] = valueRedisSerializer.serialize(values[i]);
                    }
                }
                return connection.zRem(stringRedisSerializer.serialize(key),v);
            }
        });
    }

    /**
     * 根据索引区间返回有序集合中的元素
     * @param key
     * @param start
     * @param stop
     * @return
     */
    public Set<Object> zRange(final String key,final long start, final long stop) {
        return template.execute(new RedisCallback<Set<Object>>() {
            @Override
            public Set<Object> doInRedis(RedisConnection connection) throws DataAccessException {
                Set<byte[]> bytes = connection.zRange(stringRedisSerializer.serialize(key), start, stop);
                if(bytes.size() !=0){
                    Set<Object> sets = new HashSet<Object>();
                    for (byte[] bs : bytes) {
                        sets.add(valueRedisSerializer.deserialize(bs));
                    }
                    return sets;
                } else {
                    return null;
                }
            }
        });
    }


    /**
     * key值减去value
     * @param key
     * @param value
     * @return
     */
    public long decrBy(final String key, final long value) {
        return template.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.decrBy(stringRedisSerializer.serialize(key),value);
            }
        });
    }
    public Double zScore(final String key, final Object value) {
        return template.execute(new RedisCallback<Double>() {
            @Override
            public Double doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.zScore(stringRedisSerializer.serialize(key),valueRedisSerializer.serialize(value));
            }
        });
    }




    private Map<byte[], byte[]> mapTransger1(Map<Object, Object> valueMap) {
        Map<byte[],byte[]> map= new HashMap<byte[], byte[]>();
        for (Map.Entry<Object, Object> en : valueMap.entrySet()) {
            Object field = en.getKey();
            Object value = en.getValue();
            byte[] f = valueRedisSerializer.serialize(field);
            byte[] v = valueRedisSerializer.serialize(value);
            map.put(f, v);
        }
        return map;
    }
    private Map<Object, Object> mapTransger2(Map<byte[], byte[]> valueMap) {
        Map<Object, Object> map= new HashMap<Object, Object>();
        for (Map.Entry<byte[],byte[]> en : valueMap.entrySet()) {
            byte[] field = en.getKey();
            byte[] value = en.getValue();
            Object f = valueRedisSerializer.deserialize(field);
            Object v = valueRedisSerializer.deserialize(value);
            map.put(f, v);
        }
        return map;
    }

}