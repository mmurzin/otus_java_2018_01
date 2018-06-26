package ru.otus.l0151;

public class AuthResult {
    private final boolean loginSuccessful;

    public AuthResult(boolean loginSuccessful) {
        this.loginSuccessful = loginSuccessful;
    }

    public boolean isLoginSuccessful() {
        return loginSuccessful;
    }
}
