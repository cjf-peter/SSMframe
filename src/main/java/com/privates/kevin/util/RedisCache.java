package com.privates.kevin.util;

import org.apache.ibatis.cache.Cache;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public  class RedisCache implements Cache {

    private static final Logger logger= LogManager.getLogger(RedisCache.class);
    private static JedisConnectionFactory jedisConnectionFactory;
    private final String id;

    private final ReadWriteLock readWriteLock=new ReentrantReadWriteLock();

    public RedisCache(final String id){
        System.out.println(id);
        if(id==null){
            logger.error("缓存实例编号为null");
            throw new IllegalArgumentException("Cache instances require an ID");
        }
        logger.debug("MybatisRedisCache:Mybatis 缓存编号为id="+id);
        this.id=id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void putObject(Object key, Object value) {
        JedisConnection connection=null;
        try{
            connection=jedisConnectionFactory.getConnection();
            RedisSerializer<Object> serializer=new JdkSerializationRedisSerializer();
            connection.set(serializer.serialize(key),serializer.serialize(value));
            logger.debug("存放到Redis内存数据库的数据key:"+key+",value:"+value);
        }catch (JedisConnectionException e){
            e.printStackTrace();
        }finally{
            if(connection!=null){
                connection.close();
            }
        }
    }

    @Override
    public Object getObject(Object key) {
        Object result=null;
        JedisConnection connection=null;
        try{
            connection=jedisConnectionFactory.getConnection();
            RedisSerializer<Object> serializer =new JdkSerializationRedisSerializer();
            result=serializer.deserialize(connection.get(serializer.serialize(key)));
            logger.debug("从Redis内存数据库中取到Key:"+key+"的值");
        }catch(JedisConnectionException e){
            e.printStackTrace();
        }finally{
            if(connection!=null){
                connection.close();
            }
        }
        return result;
    }

    @Override
    public Object removeObject(Object key) {
        JedisConnection connection=null;
        Object result=null;
        try{
            connection=jedisConnectionFactory.getConnection();
            RedisSerializer<Object> serializer=new JdkSerializationRedisSerializer();
            result= connection.expire(serializer.serialize(key),0);
            logger.debug("从Redis内存数据库中删除key"+key+"的数据");
        }catch(JedisConnectionException e)
        {
            e.printStackTrace();
        }finally{
            if(connection!=null){
                connection.close();
            }
        }
        return result;
    }

    @Override
    public void clear() {
        JedisConnection connection=null;
        try {
            connection=jedisConnectionFactory.getConnection();
            connection.flushDb();
            connection.flushAll();
            logger.debug("清空Redis内存数据库中所有的数据");
        }catch (JedisConnectionException e){
            e.printStackTrace();
        }finally{
            if(connection!=null){
                connection.close();
            }
        }

    }

    @Override
    public int getSize() {
        int result=0;
        JedisConnection connection=null;
        try{
            connection=jedisConnectionFactory.getConnection();
            result=Integer.valueOf(connection.dbSize().toString());
        }catch(JedisConnectionException e){
            e.printStackTrace();
        }finally {
            if(connection!=null){
                connection.close();
            }
        }
        return result;
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return this.readWriteLock;
    }
    public static void setJedisConnectionFactory(JedisConnectionFactory jedisConnectionFactory){
        RedisCache.jedisConnectionFactory=jedisConnectionFactory;
    }
}
