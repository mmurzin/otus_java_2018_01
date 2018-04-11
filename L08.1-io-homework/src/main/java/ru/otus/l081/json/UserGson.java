package ru.otus.l081.json;



import org.json.simple.JSONArray;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;

public class UserGson implements IUserGson {
    private static final String JSON_NULL = "null";

    @Override
    public String toJson(Object src) {
        if (isJsonPrimitive(src)) {
            return transformToPrimitive(src);
        } else if (isArray(src)) {
            return arrayToJson(src).toJSONString();
        } else if (isCollection(src)) {
            return collectionToJson(src).toJSONString();
        } else if (isMap(src)) {
            return mapToJson(src).toJSONString();
        }
        return transformJSONString(toJsonElement(src));
    }

    private JSONAware mapToJson(Object src) {
        JSONObject object = new JSONObject();
        if (src instanceof Map) {
            Map<Object, Object> map = (Map) src;
            Set<Map.Entry<Object, Object>> set = map.entrySet();
            for (Map.Entry<Object, Object> entry : set) {
                object.put(entry.getKey().toString(),
                        toJsonElement(entry.getValue()));
            }
        }
        return object;
    }

    private boolean isMap(Object src) {
        return src instanceof Map;
    }

    private String transformJSONString(Object obj) {
        if (obj instanceof JSONAware) {
            return ((JSONAware) obj).toJSONString();
        }
        if (obj == null) {
            return JSON_NULL;
        }
        return obj.toString();
    }

    private Object toJsonElement(Object src) {
        if (isJsonPrimitive(src)) {
            return src;
        } else if (isArray(src)) {
            return arrayToJson(src);
        } else if (isCollection(src)) {
            return collectionToJson(src);
        } else if(isMap(src)){
            return mapToJson(src);
        }
        return objectToJSONObject(src);
    }

    private Object objectToJSONObject(Object src) {
        JSONObject object = new JSONObject();
        for (Field field : getFields(src)) {
            field.setAccessible(true);
            try {
                Object fieldObject = field.get(src);
                object.put(field.getName(), toJsonElement(fieldObject));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return object;
    }

    private List<Field> getFields(Object src) {
        List<Field> fields = new ArrayList<>();
        Class clazz = src.getClass();
        while(true){
            if(clazz.equals(Object.class)){
                break;
            }
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

    private boolean isArray(Object fieldObject) {
        return fieldObject.getClass().isArray();
    }

    private boolean isCollection(Object fieldObject) {
        return fieldObject instanceof Collection;
    }

    private String transformToPrimitive(Object src) {
        return JSONValue.toJSONString(src);
    }

    private boolean isJsonPrimitive(Object src) {
        return src instanceof Boolean ||
                src instanceof String ||
                src instanceof Number ||
                src == null;
    }

    private JSONArray arrayToJson(Object fieldObject) {
        JSONArray array = new JSONArray();
        if (fieldObject.getClass().isArray()) {
            int length = Array.getLength(fieldObject);
            for (int i = 0; i < length; i++) {
                Object arrayElement = Array.get(fieldObject, i);
                array.add(toJsonElement(arrayElement));
            }
        }
        return array;
    }

    private JSONArray collectionToJson(Object fieldObject) {
        JSONArray array = new JSONArray();
        if (fieldObject instanceof Collection) {
            Collection collection = (Collection) fieldObject;
            for (Object current : collection) {
                array.add(toJsonElement(current));
            }
        }
        return array;
    }

}
