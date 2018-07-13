package ru.otus.l0161.messages;

public class LoginResultMessage extends Message{
    private final boolean isLoginSuccessfully;

    public LoginResultMessage(boolean isLoginSuccessfully) {
        this.isLoginSuccessfully = isLoginSuccessfully;
    }

    public boolean isLoginSuccessfully() {
        return isLoginSuccessfully;
    }
}
