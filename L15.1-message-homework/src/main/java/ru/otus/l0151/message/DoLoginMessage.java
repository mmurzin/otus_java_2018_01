package ru.otus.l0151.message;

import ru.otus.l0151.UserCredentials;
import ru.otus.l0151.db.DBService;

public class DoLoginMessage extends MessageToDB {
    private final UserCredentials credentials;

    public DoLoginMessage(Address from, Address to,
                          UserCredentials credentials) {
        super(from, to);
        this.credentials = credentials;
    }

    @Override
    public void exec(DBService dbService) {
        boolean isSuccessfulLogin = dbService.isSuccessfulLogin(credentials);
        dbService.getMessageSystem().sendMessage(
                new DoLoginAnswerMessage(getTo(),getFrom(),
                        credentials, isSuccessfulLogin));
    }
}
