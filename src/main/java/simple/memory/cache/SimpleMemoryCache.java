package simple.memory.cache;

import java.util.LinkedHashMap;
import java.util.Map;

public class SimpleMemoryCache<K,V> {

    LRUMap<K,V> mem;

    public SimpleMemoryCache(int maxEntries) {
        mem = new LRUMap(maxEntries);
    }

    public V get(K k) {
        synchronized(mem) {
            return mem.get(k);
        }
    }

    public void put(K k, V v) {
        synchronized(mem) {
            mem.put(k, v);
        }
    }

    private static class LRUMap<K,V> extends LinkedHashMap<K,V> {
        int maxEntries;
        public LRUMap(int maxEntries) {
            super(maxEntries / 4, 0.75f, true);
            this.maxEntries = maxEntries;
        }
        protected boolean removeEldestEntry(Map.Entry<K,V> eldest) {
            return size() > maxEntries;
        }
    }
}
