package ru.otus.l0161.messages;

public abstract class Message {
    public static final String CLASS_NAME_VARIABLE = "className";
    private String className = getClass().getName();
}
