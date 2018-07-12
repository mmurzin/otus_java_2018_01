package ru.otus.l0161.process;

import java.io.IOException;

public interface ProcessRunner {
    void start(String command) throws IOException;
}
