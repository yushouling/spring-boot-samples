package com.ysl.redis.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.ysl.redis.util.JSONUtil;
import com.ysl.redis.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class RedisServiceImpl implements RedisService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisServiceImpl.class.getName());

    @Autowired
    private RedisTemplate<String, ?> redisTemplate;

    private RedisSerializer<String> getSerializerStr() {
        return redisTemplate.getStringSerializer();
    }

    public boolean set(final String key, final String value) {
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                connection.set(serializer.serialize(key), serializer.serialize(value));
                return true;
            }
        });
        return result;
    }

    @Override
    public String get(final String key) {
        Object result = redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] value = connection.get(serializer.serialize(key));
                return serializer.deserialize(value);
            }
        });
        return (String) result;
    }

    public boolean expire(final String key, long expire) {
        return redisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }


    public RedisSerializer<Object> getSerializerObj() {
        return (RedisSerializer<Object>) redisTemplate.getDefaultSerializer();
    }

    public <T> boolean setList(String key, List<T> list) {
        String value = JSONUtil.toJson(list);
        return set(key, value);
    }

    public <T> List<T> getList(String key, Class<T> clz) {
        String json = get(key);
        if (json != null) {
            List<T> list = JSONUtil.toList(json, clz);
            return list;
        }
        return null;
    }

    public long lpush(final String key, Object obj) {
        final String value = JSONUtil.toJson(obj);
        long result = redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                long count = connection.lPush(serializer.serialize(key), serializer.serialize(value));
                return count;
            }
        });
        return result;
    }

    public long rpush(final String key, Object obj) {
        final String value = JSONUtil.toJson(obj);
        long result = redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                long count = connection.rPush(serializer.serialize(key), serializer.serialize(value));
                return count;
            }
        });
        return result;
    }

    public String lpop(final String key) {
        String result = redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] res = connection.lPop(serializer.serialize(key));
                return serializer.deserialize(res);
            }
        });
        return result;
    }

    public void setEx(final String key, final String value, final long seconds) {
        try {
            redisTemplate.execute(new RedisCallback<Object>() {
                @Override
                public Object doInRedis(RedisConnection con) throws DataAccessException {
                    con.setEx(getSerializerStr().serialize(key), seconds, getSerializerStr().serialize(value));
                    return null;
                }
            });
        } catch (Exception e) {
            LOGGER.error("更新缓存异常，key：{},{}", new Object[]{key, e});
        }
    }

    public void setEx(final String key, final String value, final long duration, final TimeUnit unit) {
        setEx(key, value, unit.toSeconds(duration));
    }

    /**
     * 批量保存缓存
     *
     * @param map
     * @param seconds 缓存时长
     * @return
     */
    public  boolean setBatchEx(final Map<String, String> map, final long seconds) {
        Boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection con) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    con.setEx(serializer.serialize(entry.getKey()), seconds,
                            getSerializerObj().serialize(entry.getValue()));
                }
                return true;
            }
        }, false, true);
        return result;
    }

    public void remove(String keys) {
        redisTemplate.delete(keys);
    }

    public Map<Integer, String> getHashAll(final String key) {
        Map<byte[], byte[]> entries = redisTemplate.execute(new RedisCallback<Map<byte[], byte[]>>() {

            public Map<byte[], byte[]> doInRedis(RedisConnection con) {
                return con.hGetAll(getSerializerStr().serialize(key));
            }
        }, true);

        if (entries == null) {
            return null;
        }

        Map<Integer, String> map = new LinkedHashMap<Integer, String>(entries.size());

        for (Map.Entry<byte[], byte[]> entry : entries.entrySet()) {
            map.put(Integer.valueOf(byteArrayToInt(entry.getKey())),
                    getSerializerStr().deserialize(entry.getValue()));
        }

        return map;
    }

    public static int byteArrayToInt(byte[] arr) {
        return (arr[0] & 0xff) | ((arr[1] << 8) & 0xff00) | ((arr[2] << 24) >>> 8) | (arr[3] << 24);
    }

    public boolean putHash(final String key, final Integer hashKey, final String value) {
        boolean flag = false;
        try {
            redisTemplate.execute(new RedisCallback<Object>() {
                public Object doInRedis(RedisConnection con) {
                    con.hSet(getSerializerStr().serialize(key), Util.intToByteArray(hashKey),
                            getSerializerStr().serialize(value));
                    return null;
                }
            }, true);
            flag = true;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return flag;

    }

    public <T> Map<Integer, T> getBatchMap(final String keyPre, final List<Integer> ids, final Class<T> clazz) {
        return redisTemplate.execute(new RedisCallback<Map<Integer, T>>() {
            public Map<Integer, T> doInRedis(RedisConnection con) throws DataAccessException {
                con.openPipeline();
                boolean pipelinedClosed = false;
                try {
                    for (Integer id : ids) {
                        con.get(getSerializerStr().serialize(keyPre + id));
                    }

                    List<Object> closePipeline = con.closePipeline();
                    pipelinedClosed = true;
                    Map<Integer, T> res = new HashMap();
                    for (int i = 0; i < closePipeline.size(); i++) {
                        T info = (T) getSerializerObj().deserialize((byte[]) closePipeline.get(i));
                        if (info != null  ) {
                            res.put(ids.get(i), info);
                        }
                    }
                    return res;
                } finally {
                    if (!pipelinedClosed) {
                        con.closePipeline();
                    }
                }
            }
        });
    }

    public  Map<Integer, String> getBatchMap(final String keyPre, final List<Integer> ids) {
        return redisTemplate.execute(new RedisCallback<Map<Integer,String>>() {
            public Map<Integer, String> doInRedis(RedisConnection con) throws DataAccessException {
                con.openPipeline();
                boolean pipelinedClosed = false;
                try {
                    for (Integer id : ids) {
                        con.get(getSerializerStr().serialize(keyPre + id));
                    }

                    List<Object> closePipeline = con.closePipeline();
                    pipelinedClosed = true;
                    Map<Integer,String> res = new HashMap();
                    for (int i = 0; i < closePipeline.size(); i++) {
                        String strVal = (String) getSerializerObj().deserialize((byte[]) closePipeline.get(i));
                        if (! StringUtils.isEmpty(strVal) ) {
                            res.put(ids.get(i), strVal);
                        }
                    }
                    return res;
                } finally {
                    if (!pipelinedClosed) {
                        con.closePipeline();
                    }
                }
            }
        });
    }

    public List<Object> batchGet(final List<String> keys) {
        return redisTemplate.execute(new RedisCallback<List<Object>>() {
            @Override
            public List<Object> doInRedis(RedisConnection redisConnection) throws DataAccessException {
                redisConnection.openPipeline();
                boolean pipelinedClosed = false;
                List<Object> closePipeline = null;
                try {
                    for (String key : keys) {
                        redisConnection.get(getSerializerStr().serialize(key));
                    }
                    closePipeline = redisConnection.closePipeline();
                    pipelinedClosed = true;
                } catch (Throwable throwable) {
                    throw throwable;
                } finally {
                    if (!pipelinedClosed) {
                        redisConnection.closePipeline();
                    }
                }
                return closePipeline;
            }
        });
    }

    /**
     * 删除缓存
     *
     * @param keys
     * @return The number of keys that were removed.
     */
    public Long remove(final Set<String> keys) {
        Long result = redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection con) throws DataAccessException {
                byte[][] byteKeys = new byte[keys.size()][];
                Iterator<String> iterator = keys.iterator();
                int i = 0;
                while (iterator.hasNext()) {
                    byteKeys[i] = getSerializerStr().serialize(iterator.next());
                    i++;
                }
                return con.del(byteKeys);
            }
        });
        return result;
    }

    public void setEx(final String key, final Object value, final long seconds) {

        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection con) throws DataAccessException {
                con.setEx(getSerializerStr().serialize(key), seconds, getSerializerStr().serialize(JSONUtil.toJson(value)));
                return null;
            }
        });

    }

    public void setEx(final String key, final Object value, final long duration, final TimeUnit unit) {
        setEx(key, value, unit.toSeconds(duration));
    }

    public <T> T get(final String key, Class<T> clz) {
        Object result = redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] value = connection.get(serializer.serialize(key));
                return serializer.deserialize(value);
            }
        });
        return JSONUtil.toBean(result == null ? "" : result.toString(), clz);
    }

}