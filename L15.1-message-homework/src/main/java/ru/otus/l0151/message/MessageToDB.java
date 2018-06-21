package ru.otus.l0151.message;

import ru.otus.l0151.db.DBService;

public abstract class MessageToDB extends Message {

    public MessageToDB(Address from, Address to) {
        super(from, to);
    }


    @Override
    public void exec(Addressee addressee) {
        if(addressee instanceof DBService){
            exec((DBService)addressee);
        }
    }

    public abstract void exec(DBService dbService);
}
