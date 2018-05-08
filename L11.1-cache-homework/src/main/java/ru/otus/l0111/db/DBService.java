package ru.otus.l0111.db;

import ru.otus.l0111.models.UserDataSet;

import java.util.List;

public interface DBService {

    void save(UserDataSet entity);

    void setCacheEnabled(boolean state);

    UserDataSet load(long id);

    List<UserDataSet> readAll();

    void shutdown();

}
