package ru.otus.l0161.messages;

public interface MessageWorker {
    Message pool();
    void close();
    Message take() throws InterruptedException;
    void send(Message message);
}
