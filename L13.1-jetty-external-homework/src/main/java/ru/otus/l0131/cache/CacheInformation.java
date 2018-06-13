package ru.otus.l0131.cache;

public interface CacheInformation {

    int getRequestCount();

    int getHitCount();

    int getMissCount();

    int getCacheSize();

    int getCurrentCount();
}
