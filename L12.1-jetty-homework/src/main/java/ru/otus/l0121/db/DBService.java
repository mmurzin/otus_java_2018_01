package ru.otus.l0121.db;

import ru.otus.l0121.cache.CacheEngine;
import ru.otus.l0121.models.UserDataSet;

import java.util.List;

public interface DBService {

    void save(UserDataSet entity);

    void setCacheEnabled(boolean state);

    UserDataSet load(long id);

    List<UserDataSet> readAll();

    void truncateTables(Class... items);

    void shutdown();

    CacheEngine getCacheEngine();

}
