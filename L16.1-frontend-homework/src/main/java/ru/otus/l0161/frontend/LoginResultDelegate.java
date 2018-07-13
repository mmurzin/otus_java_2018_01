package ru.otus.l0161.frontend;

import java.io.IOException;

public interface LoginResultDelegate {
    void publishResult(boolean isLoginSuccessfully) throws IOException;
}
