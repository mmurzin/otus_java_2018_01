package ru.otus.l0111.cache;

public interface CacheEngine<K, V> {

    void put(CacheElement<K, V> element);

    V get(K key);

    int getHintCount();

    int getMissCount();

    void dispose();
}
