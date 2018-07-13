package ru.otus.l0161.frontend;

import ru.otus.l0161.cache.CacheInformation;

public interface CacheResultDelegate {
    void publishCacheInformation(CacheInformation cacheInformation);
}
