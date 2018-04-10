package ru.otus.l081.json;


import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.lang.reflect.Field;

public class UserGson implements IUserGson {

    @Override
    public String toJson(Object src) {
        if (isJsonPrimitive(src)) {
            return primitiveToJson(src);
        } else {
            return objectToJson(src);
        }
    }

    private String objectToJson(Object src) {
        JSONObject jsonObject = new JSONObject();
        for (Field field : src.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object fieldObject = field.get(src);
                if (fieldObject != null &&
                        fieldObject.getClass().isArray()) {
                    System.out.println("Field " + field);
                }
                jsonObject.put(field.getName(), fieldObject);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return jsonObject.toJSONString();
    }

    private String primitiveToJson(Object src) {
        return JSONValue.toJSONString(src);
    }

    private boolean isJsonPrimitive(Object src) {
        return src instanceof Boolean ||
                src instanceof String ||
                src instanceof Number;
    }


}
