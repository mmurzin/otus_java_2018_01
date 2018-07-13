package ru.otus.l0161.messages;

import ru.otus.l0161.cache.CacheInformation;

public class CacheResultMessage extends Message{
    private final CacheInformation cacheInformation;

    public CacheResultMessage(CacheInformation cacheInformation) {
        this.cacheInformation = cacheInformation;
    }

    public CacheInformation getCacheInformation() {
        return cacheInformation;
    }
}
