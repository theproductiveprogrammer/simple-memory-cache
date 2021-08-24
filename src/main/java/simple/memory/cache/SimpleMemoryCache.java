package simple.memory.cache;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/*      way/
 * This is an in-memory cache that uses a LRU strategy for access. Because
 * it is dependent on {@link java.util.LinkedHashMap LinkedHashMap} we can't
 * use a sophisticated eviction stragety and so we use a GC for periodic
 * collection of expired records.
 */
public class SimpleMemoryCache<K,V> {

    LRUMap<K,CleanableCacheObject> mem;
    int ttl;

    /*      understand/
     * creates a LRU in-memory cache with the given number
     * of max entries and the time to live before expiry
     * of each entry.
     *      way/
     * creates an LRU cache based on {@link java.util.LinkedHashMap LinkedHashMap}
     * and start a garbage collection cycle.
     */
    public SimpleMemoryCache(int maxEntries, int ttl) {
        mem = new LRUMap(maxEntries);

        this.ttl = ttl;
        if(ttl > 0) {
            Thread t = new Thread(new Runnable() {
                public void run() {
                    while(true) {
                        try {
                            Thread.sleep(60*1000);
                        } catch(InterruptedException e) {}
                        cleanup();
                    }
                }
            });
            t.setDaemon(true);
            t.start();
        }
    }

    /*      way/
     * lock the cache an clear out objects whose time has expired
     */
    void cleanup() {
        long now = System.currentTimeMillis();
        List<K> todelete = new ArrayList();

        synchronized(mem) {
            Iterator<Map.Entry<K,CleanableCacheObject>> itr = mem.entrySet().iterator();
            while(itr.hasNext()) {
                Map.Entry<K,CleanableCacheObject> entry = itr.next();
                CleanableCacheObject c = entry.getValue();
                if(c != null && (now > (ttl + c.ts))) itr.remove();
            }
        }
        Thread.yield();
    }

    /*      way/
     * return the associated value for this key, updating the access
     * time - returning null if missing
     */
    public V get(K k) {
        synchronized(mem) {
            CleanableCacheObject c = mem.get(k);
            if(c == null) return null;
            c.ts = System.currentTimeMillis();
            return c.v;
        }
    }

    /*      way/
     * add a new key value pair to the cache
     */
    public void put(K k, V v) {
        synchronized(mem) {
            CleanableCacheObject c = new CleanableCacheObject(v);
            mem.put(k, c);
        }
    }

    /*      way/
     * create a new LRU cache by using {@link java.util.LinkedHashMap LinkedHashMap}
     * with a 0.75f load factor and a 1/4 initial size which are good
     * defaults to have. Passing `accessorder=true` ensures that the map
     * keeps ordered by access which is ideal for LRU and we then use a
     * simple `removeEldestEntry` eviction strategy.
     */
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

    /*      understand/
     * wraps the value so we can detect when the object is too old and
     * clean it using the GC
     */
    private class CleanableCacheObject {
        long ts;
        V v;

        public CleanableCacheObject(V v) {
            this.v = v;
            this.ts = System.currentTimeMillis();
        }
    }
}
