package ru.otus.l091;

import ru.otus.l091.interfaces.DBResultHandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BaseExecutor {

    final Connection connection;

    public BaseExecutor(Connection connection) {
        this.connection = connection;
    }

    public <T> T execQuery(String query, DBResultHandler<T> handler)
            throws SQLException {
        try(Statement stmt = connection.createStatement()) {
            stmt.execute(query);
            ResultSet result = stmt.getResultSet();
            return handler.handle(result);
        }
    }

    public int execUpdate(String update) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(update);
            return stmt.getUpdateCount();
        }
    }
}
