package ru.otus.l0151.message;

import ru.otus.l0151.UserCredentials;
import ru.otus.l0151.frontend.FrontendService;

public class DoLoginAnswerMessage extends MessageToFrontend {
    private final boolean isSuccessfulLogin;
    private final UserCredentials userCredentials;

    public DoLoginAnswerMessage(Address from, Address to,
                                UserCredentials credentials,
                                boolean isSuccessfulLogin) {
        super(from, to);
        this.isSuccessfulLogin = isSuccessfulLogin;
        this.userCredentials = credentials;
    }


    @Override
    public void exec(FrontendService frontendService) {
        frontendService.publishLoginResult(userCredentials, isSuccessfulLogin);
    }
}
