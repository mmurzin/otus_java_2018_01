package ru.otus.l0161.db;



import ru.otus.l0161.UserCredentials;
import ru.otus.l0161.cache.CacheEngine;
import ru.otus.l0161.models.UserDataSet;

import java.util.List;

public interface DBService  {

    void save(UserDataSet entity);

    void setCacheEnabled(boolean state);

    UserDataSet load(long id);

    List<UserDataSet> readAll();

    void truncateTables(Class... items);

    void shutdown();

    CacheEngine getCacheEngine();

    boolean isSuccessfulLogin(UserCredentials credentials);

}
