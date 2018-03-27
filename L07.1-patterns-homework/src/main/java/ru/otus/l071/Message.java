package ru.otus.l071;

public abstract class Message {

    enum Type {
        RESET
    }

    private final Type type;


    public Message(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }
}
