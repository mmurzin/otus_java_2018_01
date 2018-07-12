package ru.otus.l0161;

import com.google.gson.Gson;

import java.util.Objects;

public class UserCredentials {
    private final String login;
    private final String password;

    public UserCredentials(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public static UserCredentials fromJson(String data) {
        return new Gson().fromJson(data, UserCredentials.class);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserCredentials)) return false;
        UserCredentials that = (UserCredentials) o;
        return Objects.equals(login, that.login) &&
                Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {

        return Objects.hash(login, password);
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "UserCredentials{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
