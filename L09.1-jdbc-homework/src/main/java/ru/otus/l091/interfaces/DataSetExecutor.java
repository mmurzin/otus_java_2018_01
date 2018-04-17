package ru.otus.l091.interfaces;

import ru.otus.l091.models.DataSet;

public interface DataSetExecutor {
    <T extends DataSet> void save(T entity);

    <T extends DataSet> T load(long id, Class<T> clazz);
}
