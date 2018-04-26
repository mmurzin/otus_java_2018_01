package ru.otus.l0101;

import ru.otus.l0101.models.UserDataSet;

import java.util.List;

public interface DBService {

    void save(UserDataSet entity);

    UserDataSet load(long id);

    List<UserDataSet> readAll();

    void shutdown();

}
