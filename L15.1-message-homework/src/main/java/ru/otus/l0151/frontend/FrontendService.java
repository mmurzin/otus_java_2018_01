package ru.otus.l0151.frontend;

import ru.otus.l0151.UserCredentials;
import ru.otus.l0151.message.Addressee;
import ru.otus.l0151.websocket.LoginSocket;

public interface FrontendService extends Addressee {

    void doLogin(UserCredentials credentials, LoginSocket loginSocket);

    void publishLoginResult(UserCredentials credentials, boolean isSuccessfulLogin);

    void removeSocket(LoginSocket loginSocket);
}
