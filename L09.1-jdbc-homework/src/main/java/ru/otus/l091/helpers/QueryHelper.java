package ru.otus.l091.helpers;

import ru.otus.l091.models.DataSet;

import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class QueryHelper {

    private QueryHelper() {
        throw new UnsupportedOperationException();
    }

    public static String insertQuery(String tableName,
                                     List<String> names,
                                     List<String> values) {
        int idPosition = getDataSetIdPosition(names);
        if (idPosition >= 0) {
            names.remove(idPosition);
            values.remove(idPosition);
        }
        final String fieldNames = names
                .stream()
                .map(s -> "`" + s + "`")
                .collect(Collectors.joining(", ", "(", ")"));

        final String fieldValues = values
                .stream()
                .map(s -> "'" + s + "'")
                .collect(Collectors.joining(", ", "(", ")"));

        return "INSERT INTO `" + tableName + "` " + fieldNames + " VALUES " + fieldValues;
    }

    public static String updateQuery(String tableName,
                                     List<String> names,
                                     List<String> values,
                                     long id) {

        int idPosition = getDataSetIdPosition(names);
        if (idPosition >= 0) {
            names.remove(idPosition);
            values.remove(idPosition);
        }

        int size = Math.min(names.size(), values.size());
        StringBuilder builder = new StringBuilder();
        builder.append("UPDATE `").append(tableName).append("` SET ");
        StringJoiner stringJoiner = new StringJoiner(",");
        for (int i = 0; i < size; i++) {
            stringJoiner.add("`" + names.get(i) + "`='" + values.get(i) + "'");
        }
        builder.append(stringJoiner.toString());
        builder.append(" WHERE `" + DataSet.ID_NAME + "`='")
                .append(String.valueOf(id))
                .append("'");
        return builder.toString();
    }

    private static int getDataSetIdPosition(List<String> names) {
        return names.indexOf(DataSet.ID_NAME);
    }

    public static String selectQuery(String tableName, String idName, String idValue) {
        return "SELECT * FROM `" + tableName + "` WHERE `" + idName + "`='" + idValue + "'";
    }
}
