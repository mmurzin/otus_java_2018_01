package ru.otus.l061;

import java.util.List;

public interface ICashMachine {
    void depositMoney(List<Note> notes);

    List<Note> getMoney(long value);

    long getBalance();

}
