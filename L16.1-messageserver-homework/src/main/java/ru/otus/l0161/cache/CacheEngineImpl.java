package ru.otus.l0161.cache;

import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Function;

public class CacheEngineImpl<K, V> implements CacheEngine<K, V> {

    public static final int DEFAULT_CACHE_SIZE = 60;
    public static final int DB_CACHE_LIFE_TIME = 0;
    public static final int DB_CACHE_IDLE_TIME = 0;

    private static final int TIME_THRESHOLD_MS = 5;

    private final int maxElements;
    private final long lifeTimeMs;
    private final long idleTimeMs;
    private final boolean isEternal;

    private final Map<K, SoftReference<CacheElement<K, V>>> elements =
            new LinkedHashMap<>();
    private final Timer timer = new Timer();

    private int hit = 0;
    private int miss = 0;
    private int requestCount = 0;

    public CacheEngineImpl() {
        this.maxElements = DEFAULT_CACHE_SIZE;
        this.lifeTimeMs = DB_CACHE_LIFE_TIME;
        this.idleTimeMs = DB_CACHE_IDLE_TIME;
        this.isEternal = false;
    }

    public CacheEngineImpl(int maxElements,
                           long lifeTimeMs,
                           long idleTimeMs,
                           boolean isEternal) {
        this.maxElements = maxElements;
        this.lifeTimeMs = lifeTimeMs > 0 ? lifeTimeMs : 0;
        this.idleTimeMs = idleTimeMs > 0 ? idleTimeMs : 0;
        this.isEternal = lifeTimeMs == 0 && idleTimeMs == 0 || isEternal;
    }

    @Override
    public void put(CacheElement<K, V> element) {
        if (elements.size() == maxElements) {
            K firstKey = elements.keySet().iterator().next();
            elements.remove(firstKey);
        }
        K elementKey = element.getKey();
        elements.put(elementKey, new SoftReference<>(element));

        if (!isEternal) {
            if (lifeTimeMs != 0) {
                TimerTask lifeTimerTask = getTimerTask(elementKey, lifeElement -> lifeElement.getCreationTime() + lifeTimeMs);
                timer.schedule(lifeTimerTask, lifeTimeMs);
            }
            if (idleTimeMs != 0) {
                TimerTask idleTimerTask = getTimerTask(elementKey, idleElement -> idleElement.getLastAccessTime() + idleTimeMs);
                timer.schedule(idleTimerTask, idleTimeMs, idleTimeMs);
            }
        }
    }

    @Override
    public V get(K key) {
        requestCount++;
        SoftReference<CacheElement<K, V>> reference =
                elements.get(key);
        CacheElement<K, V> element = null;
        if (reference != null) {
            element = reference.get();
        }
        if (element != null) {
            hit++;
            element.setAccessed();
            return element.getValue();
        } else {
            miss++;
        }
        return null;
    }

    @Override
    public CacheInformation getCacheInformation() {
        return new CacheInformation() {
            @Override
            public int getRequestCount() {
                return requestCount;
            }

            @Override
            public int getHitCount() {
                return hit;
            }

            @Override
            public int getMissCount() {
                return miss;
            }

            @Override
            public int getCacheSize() {
                return DEFAULT_CACHE_SIZE;
            }

            @Override
            public int getCurrentCount() {
                return elements.size();
            }
        };
    }

    public static CacheInformation emptyInformation() {
        return new CacheInformation() {
            @Override
            public int getRequestCount() {
                return -1;
            }

            @Override
            public int getHitCount() {
                return -1;
            }

            @Override
            public int getMissCount() {
                return -1;
            }

            @Override
            public int getCacheSize() {
                return -1;
            }

            @Override
            public int getCurrentCount() {
                return -1;
            }
        };
    }

    @Override
    public void dispose() {
        timer.cancel();
    }

    private TimerTask getTimerTask(final K key, Function<CacheElement<K, V>, Long> timeFunction) {
        return new TimerTask() {
            @Override
            public void run() {
                SoftReference<CacheElement<K, V>> reference = elements.get(key);
                CacheElement<K, V> element = reference.get();
                if (element == null ||
                        isT1BeforeT2(timeFunction.apply(element), System.currentTimeMillis())) {
                    elements.remove(key);
                    this.cancel();
                }
            }
        };
    }

    private boolean isT1BeforeT2(long t1, long t2) {
        return t1 < t2 + TIME_THRESHOLD_MS;
    }

}
