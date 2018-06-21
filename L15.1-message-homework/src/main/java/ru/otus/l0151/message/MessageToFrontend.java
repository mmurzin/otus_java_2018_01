package ru.otus.l0151.message;

import ru.otus.l0151.frontend.FrontendService;

public abstract class MessageToFrontend extends Message {
    public MessageToFrontend(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Addressee addressee) {
        if(addressee instanceof FrontendService){
            exec((FrontendService) addressee);
        }
    }

    public abstract void exec(FrontendService frontendService);
}
