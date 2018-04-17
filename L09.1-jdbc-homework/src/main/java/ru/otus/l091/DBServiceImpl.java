package ru.otus.l091;

import ru.otus.l091.helpers.ConnectionHelper;
import ru.otus.l091.helpers.DataSetHelper;
import ru.otus.l091.helpers.QueryHelper;
import ru.otus.l091.interfaces.DBService;
import ru.otus.l091.models.DataSet;
import ru.otus.l091.models.UserDataSet;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DBServiceImpl implements DBService {

    private static final String CREATE_TABLE_USER = "CREATE TABLE if not exists `" +
            TablesEnum.getTableName(UserDataSet.class) + "`  (\n" +
            "  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,\n" +
            "  `name` VARCHAR(255) NULL,\n" +
            "  `age` INT(3) NULL,\n" +
            "  PRIMARY KEY (`id`));";
    private static final String DELETE_USERS = "DROP TABLE `" + TablesEnum.getTableName(UserDataSet.class) + "`";
    private static DBServiceImpl instance;
    private Connection connection;

    private DBServiceImpl(Connection connection) {
        this.connection = connection;
    }

    public static DBServiceImpl getInstance() {
        if (instance == null) {
            instance = new DBServiceImpl(ConnectionHelper.getConnection());
        }
        return instance;
    }

    @Override
    public void close() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }

    @Override
    public void prepareTables() throws SQLException {
        BaseExecutor executor = new BaseExecutor(connection);
        executor.execUpdate(CREATE_TABLE_USER);
    }

    @Override
    public void deleteTables() throws SQLException {
        BaseExecutor executor = new BaseExecutor(connection);
        executor.execUpdate(DELETE_USERS);
    }

    @Override
    public <T extends DataSet> void save(T entity) {
        String tableName = TablesEnum.getTableName(entity.getClass());
        if (tableName == null) {
            return;
        }

        Map<String, String> objectDataMap =
                DataSetHelper.getObjectData(entity);

        List<String> names = new ArrayList<>(objectDataMap.size());
        List<String> values = new ArrayList<>(objectDataMap.size());

        objectDataMap.forEach((name, value) -> {
            names.add(name);
            values.add(value);
        });
        BaseExecutor executor = new BaseExecutor(connection);
        final String query;
        if (DataSetHelper.isNewEntity(entity)) {
            query = QueryHelper.insertQuery(tableName,
                    names,
                    values);
            try {
                long id = executor.execInsert(query);
                entity.setId(id);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            query = QueryHelper.updateQuery(
                    tableName,
                    names,
                    values,
                    entity.getId());

            try {
                executor.execUpdate(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> clazz) throws SQLException {
        BaseExecutor executor = new BaseExecutor(connection);
        final String tableName = TablesEnum.getTableName(clazz);
        final String query = QueryHelper.selectQuery(tableName,
                DataSet.ID_NAME,
                String.valueOf(id));
        return executor.execQuery(query, resultSet -> {
            T instance = null;
            try {
                instance = clazz.getConstructor().newInstance();
                ResultSetMetaData metaData = resultSet.getMetaData();
                Map<String, Field> fieldsMap = DataSetHelper.getObjectFields(instance);
                while (resultSet.next()) {
                    for (int i = 1; i <= metaData.getColumnCount(); i++) {
                        String name = metaData.getColumnName(i);
                        Field currentField = fieldsMap.get(name);
                        if (currentField == null) {
                            throw new RuntimeException("Field " + name + " not founded");
                        }
                        currentField.setAccessible(true);

                        Object value = getValue(
                                resultSet,
                                metaData.getColumnType(i),
                                i);

                        currentField.set(instance, value);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return instance;
        });
    }

    private Object getValue(ResultSet resultSet, int type, int index) {
        try {
            switch (type) {
                case 12:
                    return resultSet.getString(index);
                case 2:
                case -5:
                    return resultSet.getLong(index);
                case 5:
                case 4:
                    return resultSet.getInt(index);
                default:
                    throw new RuntimeException("ERROR TYPE " + type + " BY INDEX " + index);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private enum TablesEnum {
        USER_DATA_SET(UserDataSet.class, "users"),
        DEFAULT_DATA_SET(DataSet.class, null);

        private final Class clazz;
        private final String tableName;

        TablesEnum(Class clazz, String tableName) {
            this.clazz = clazz;
            this.tableName = tableName;
        }

        static String getTableName(Class clazz) {
            for (TablesEnum current : TablesEnum.values()) {
                if (current.clazz.equals(clazz)) {
                    return current.tableName;
                }
            }
            return null;
        }
    }


}
