package simple.memory.cache;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class SimpleMemoryCache<K,V> {

    LRUMap<K,CleanableCacheObject> mem;
    int ttl;

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

    public V get(K k) {
        synchronized(mem) {
            CleanableCacheObject c = mem.get(k);
            if(c == null) return null;
            c.ts = System.currentTimeMillis();
            return c.v;
        }
    }

    public void put(K k, V v) {
        synchronized(mem) {
            CleanableCacheObject c = new CleanableCacheObject(v);
            mem.put(k, c);
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

    private class CleanableCacheObject {
        long ts;
        V v;

        public CleanableCacheObject(V v) {
            this.v = v;
            this.ts = System.currentTimeMillis();
        }
    }
}
