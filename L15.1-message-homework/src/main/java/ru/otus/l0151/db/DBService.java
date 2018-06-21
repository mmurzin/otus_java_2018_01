package ru.otus.l0151.db;


import ru.otus.l0151.UserCredentials;
import ru.otus.l0151.cache.CacheEngine;
import ru.otus.l0151.message.Addressee;
import ru.otus.l0151.message.MessageSystem;
import ru.otus.l0151.models.UserDataSet;

import java.util.List;

public interface DBService extends Addressee {

    void save(UserDataSet entity);

    void setCacheEnabled(boolean state);

    UserDataSet load(long id);

    List<UserDataSet> readAll();

    void truncateTables(Class... items);

    void shutdown();

    CacheEngine getCacheEngine();

    boolean isSuccessfulLogin(UserCredentials credentials);

    MessageSystem getMessageSystem();
}
