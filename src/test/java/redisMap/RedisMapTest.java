package redisMap;

import org.junit.jupiter.api.*;
import org.redis.RedisMap;
import redis.clients.jedis.Jedis;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RedisMapTest {
    static private RedisMap map;

    @BeforeAll
    static void setup() {
        map = new RedisMap("testMap");
    }

    @BeforeEach
    void clearMap() {
        map.clear();
    }

    @AfterAll
    static void tearDown() {
        map.getJedis().close();
    }

    @Test
    void testPutAndGet() {
        assertNull(map.put("key1", "value1"));;
        assertEquals("value1", map.put("key1", "value2"));

        assertEquals("value2", map.get("key1"));
        assertNull(map.get("key2"));
    }

    @Test
    void testContainsKey() {
        map.put("key1", "value1");

        assertTrue(map.containsKey("key1"));
        assertFalse(map.containsKey("key2"));
    }

    @Test
    void testSize() {
        assertEquals(0, map.size());

        map.put("key1", "value1");

        assertEquals(1, map.size());
    }

    @Test
    void testRemove() {
        map.put("key1", "value1");

        String value = map.remove("key1");

        assertEquals("value1", value);
        assertNull(map.get("key1"));
    }

    @Test
    void testClear() {
        map.put("key1", "value1");

        map.clear();

        assertNull(map.get("key1"));
        assertEquals(0, map.size());
    }

    @Test
    void testKeySet() {
        map.put("key1", "value1");
        map.put("key2", "value2");

        Set<String> keys = map.keySet();

        assertEquals(2, keys.size());
        assertTrue(keys.contains("key1"));
        assertTrue(keys.contains("key2"));
    }

    @Test
    void testValues() {
        map.put("key1", "value1");
        map.put("key2", "value2");

        Collection<String> values = map.values();

        assertTrue(values.contains("value1"));
        assertTrue(values.contains("value2"));
        assertEquals(2, values.size());
    }

    @Test
    void testEntrySet() {
        map.put("key1", "value1");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            assertEquals("key1", entry.getKey());
            assertEquals("value1", entry.getValue());
        }
    }
}