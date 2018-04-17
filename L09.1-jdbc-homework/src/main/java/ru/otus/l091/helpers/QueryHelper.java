package ru.otus.l091.helpers;

import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class QueryHelper {

    private QueryHelper() {
        throw new UnsupportedOperationException();
    }

    public static String insertQuery(String tableName,
                                     List<String> names) {
        final String fieldNames = names
                .stream()
                .map(s -> "`" + s + "`")
                .collect(Collectors.joining(", ", "(", ")"));

        final String fieldValues = names
                .stream()
                .map(s -> "?")
                .collect(Collectors.joining(", ", "(", ")"));

        return "INSERT INTO `" + tableName + "` " + fieldNames + " VALUES " + fieldValues;
    }

    public static String updateQuery(String tableName,
                                     List<String> names,
                                     List<String> values,
                                     long id) {
        int size = Math.min(names.size(), values.size());
        StringBuilder builder = new StringBuilder();
        builder.append("UPDATE `").append(tableName).append("` SET ");
        StringJoiner stringJoiner = new StringJoiner(",");
        for (int i = 0; i < size; i++) {
            stringJoiner.add("`"+names.get(i) + "`=" + values.get(i));
        }
        builder.append(stringJoiner.toString());
        builder.append(" WHERE `id`=").append(String.valueOf(id));
        return builder.toString();
    }
}
