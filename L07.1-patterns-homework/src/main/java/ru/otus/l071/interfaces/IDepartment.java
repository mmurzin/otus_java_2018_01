package ru.otus.l071.interfaces;

import ru.otus.l071.CashMachine;

public interface IDepartment {

    long getBalances();
    void resetMachines();

    //observer
    void register(ICashMachine observer);

    boolean unregister(ICashMachine observer);

    //memento
    void saveState(CashMachine cashMachine);
}
