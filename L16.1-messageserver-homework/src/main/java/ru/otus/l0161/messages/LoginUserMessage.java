package ru.otus.l0161.messages;

import ru.otus.l0161.UserCredentials;

public class LoginUserMessage extends Message {
    private final UserCredentials userCredentials;

    public LoginUserMessage(UserCredentials userCredentials) {
        this.userCredentials = userCredentials;
    }

    public UserCredentials getUserCredentials() {
        return userCredentials;
    }
}
