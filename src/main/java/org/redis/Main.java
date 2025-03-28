package org.redis;

public class Main {
    public static void main(String[] args) {
        RedisMap redisMap = new RedisMap("mainMap");

        redisMap.put("mainKey1", "mainValue1");
        redisMap.put("mainKey2", "mainValue2");

        String value1 = redisMap.get("mainKey1");

        System.out.println(value1);
        System.out.println(redisMap.containsKey("mainKey2"));
    }
}