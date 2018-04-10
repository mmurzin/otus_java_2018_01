package ru.otus.l081.json;

@FunctionalInterface
public interface IUserGson {
    String toJson(Object src);
}
