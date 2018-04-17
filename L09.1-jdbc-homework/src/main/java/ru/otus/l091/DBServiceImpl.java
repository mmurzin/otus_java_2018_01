package ru.otus.l091;

import ru.otus.l091.helpers.ConnectionHelper;
import ru.otus.l091.helpers.DataSetHelper;
import ru.otus.l091.helpers.QueryHelper;
import ru.otus.l091.interfaces.DBService;
import ru.otus.l091.interfaces.DataSetExecutor;
import ru.otus.l091.models.DataSet;
import ru.otus.l091.models.UserDataSet;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DBServiceImpl implements DBService, DataSetExecutor {

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
        final String query;
        if(DataSetHelper.isNewEntity(entity)){
            query = QueryHelper.insertQuery(tableName, names);
        }else{
            query = QueryHelper.updateQuery(
                    tableName,
                    names,
                    values,
                    entity.getId());
        }

        System.out.println("QUERY: " + query);
    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> clazz) {
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
