package ru.otus.l0151.cache;

public interface CacheInformation {

    int getRequestCount();

    int getHitCount();

    int getMissCount();

    int getCacheSize();

    int getCurrentCount();
}
