package ru.otus.l0151;

import ru.otus.l0151.message.Address;
import ru.otus.l0151.message.MessageSystem;

public class MessageContext {
    private final MessageSystem messageSystem;
    private Address frontAddress;
    private Address dbAddress;

    public MessageContext() {
        this.messageSystem = MessageSystem.getMessageSystem();
        this.frontAddress = Address.getFrontAddress();
        this.dbAddress = Address.getDbAddress();
    }

    public MessageSystem getMessageSystem() {
        return messageSystem;
    }

    public Address getFrontAddress() {
        return frontAddress;
    }

    public Address getDbAddress() {
        return dbAddress;
    }
}
