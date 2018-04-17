package ru.otus.l091.interfaces;

import ru.otus.l091.models.DataSet;

import java.sql.SQLException;

public interface DBService extends AutoCloseable {

    void prepareTables() throws SQLException;

    void deleteTables() throws SQLException;

    <T extends DataSet> void save(T entity);

    <T extends DataSet> T load(long id, Class<T> clazz) throws SQLException;
}
