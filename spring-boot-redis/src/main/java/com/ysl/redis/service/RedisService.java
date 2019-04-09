package com.ysl.redis.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public interface RedisService {
    
    public boolean set(String key, String value);

    public String get(String key);

    public boolean expire(String key, long expire);

    public <T> boolean setList(String key, List<T> list);

    public <T> List<T> getList(String key, Class<T> clz);

    public long lpush(String key, Object obj);

    public long rpush(String key, Object obj);

    public String lpop(String key);

    boolean setBatchEx(Map<String, String> addRedisBatchMap, long seconds);

    void remove(String keys);
    
    Long remove(final Set<String> keys);

    Map<Integer, String> getHashAll(final String key);

    boolean putHash(final String key, final Integer hashKey, final String value);

    void setEx(final String key, final String value, final long seconds);

    void setEx(final String key, final String value, final long duration, final TimeUnit unit);
    
    void setEx(final String key, final Object value, final long seconds);

    void setEx(final String key, final Object value, final long duration, final TimeUnit unit);
    
    public <T> T get(String key, Class<T> clz);

}
