package ru.otus.l0121.cache;

public interface CacheEngine<K, V> {

    void put(CacheElement<K, V> element);

    V get(K key);

    CacheInformation getCacheInformation();

    void dispose();
}
