package ru.otus.l091.interfaces;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface DBResultHandler<T> {
    T handle(ResultSet resultSet) throws SQLException;
}

