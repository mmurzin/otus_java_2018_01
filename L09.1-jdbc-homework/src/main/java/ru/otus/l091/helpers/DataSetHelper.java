package ru.otus.l091.helpers;

import ru.otus.l091.models.DataSet;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class DataSetHelper {

    private DataSetHelper() {
        throw new UnsupportedOperationException("Private constructor");
    }

    public static <T extends DataSet>
    Map<String, String> getObjectData(T src) {
        Map<String, String> map = new HashMap<>();
        Class clazz = src.getClass();
        try {
            while (true) {
                if (clazz.equals(Object.class)) {
                    break;
                }
                Field[] currentFields = clazz.getDeclaredFields();
                for (Field field : currentFields) {
                    field.setAccessible(true);
                    boolean isStatic = Modifier.isStatic(field.getModifiers());
                    boolean isTransient = Modifier.isTransient(field.getModifiers());
                    if (!isStatic && !isTransient) {
                        map.put(field.getName(), String.valueOf(field.get(src)));
                    }
                }
                clazz = clazz.getSuperclass();
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static boolean isNewEntity(DataSet entity) {
        return entity.getId() == 0;
    }
}
