package ru.otus.l0151.cache;

public class CacheElement<K, V> {
    private final K key;
    private final V value;
    private final long creationTime;
    private long lastAccessTime;


    public CacheElement(K key, V value) {
        this.key = key;
        this.value = value;
        this.creationTime = getCurrentTime();
        this.lastAccessTime = getCurrentTime();
    }

    protected long getCurrentTime() {
        return System.currentTimeMillis();
    }

    public K getKey() {
        return key;
    }

    public void setAccessed() {
        lastAccessTime = getCurrentTime();
    }

    public long getCreationTime() {
        return creationTime;
    }

    public long getLastAccessTime() {
        return lastAccessTime;
    }

    public V getValue() {
        return value;
    }
}
