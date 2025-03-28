package org.redis;

import lombok.Getter;
import lombok.Setter;
import redis.clients.jedis.Jedis;
import java.util.*;

@Getter
@Setter
public class RedisMap implements Map<String, String> {
    private Jedis jedis;
    private String mapName;

    public RedisMap(Jedis jedis, String mapName) {
        this.jedis = Objects.requireNonNull(jedis);
        this.mapName = Objects.requireNonNull(mapName);
    }

    public RedisMap(String mapName) {
        this(new Jedis(), mapName);
    }

    @Override
    public int size() {
        return ((Long)jedis.hlen(mapName)).intValue();
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        return jedis.hexists(mapName, (String) key);
    }

    @Override
    public boolean containsValue(Object value) {
        return values().contains(value);
    }

    @Override
    public String get(Object key) {
        return jedis.hget(mapName, (String) key);
    }

    @Override
    public String put(String key, String value) {
        String previous = get(key);
        jedis.hset(mapName, key, value);
        return previous;
    }

    @Override
    public String remove(Object key) {
        if(key instanceof String) {
            String previous = get(key);
            jedis.hdel(mapName, (String) key);
            return previous;
        }
        throw new ClassCastException();
    }

    @Override
    public void putAll(Map<? extends String, ? extends String> m) {
        jedis.hset(mapName, (Map<String, String>) m);
    }

    @Override
    public void clear() {
        jedis.del(mapName);
    }

    @Override
    public Set<String> keySet() {
        return jedis.hkeys(mapName);
    }

    @Override
    public Collection<String> values() {
        return jedis.hvals(mapName);
    }

    @Override
    public Set<Entry<String, String>> entrySet() {
        Map<String, String> map = jedis.hgetAll(mapName);
        return map.entrySet();
    }
}