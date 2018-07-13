package ru.otus.l0161.frontend;


import ru.otus.l0161.UserCredentials;

public interface FrontendService{

    void doLogin(UserCredentials credentials, LoginResultDelegate loginResultDelegate);
}
