package ru.otus.l071.interfaces;

import ru.otus.l071.CashMachineMemento;
import ru.otus.l071.Message;
import ru.otus.l071.Note;

import java.util.List;

public interface ICashMachine {
    void depositMoney(List<Note> notes);

    List<Note> getMoney(long value);

    long getBalance();

    //observer pattern
    void notify(Message message);

    //memento
    CashMachineMemento saveToMemento();
    void restoreFromMemento(CashMachineMemento memento);

}
