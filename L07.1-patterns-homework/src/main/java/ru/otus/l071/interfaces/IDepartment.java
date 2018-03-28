package ru.otus.l071.interfaces;

public interface IDepartment {

    long getBalances();
    void resetMachines();

    //observer
    void register(ICashMachine observer);

    boolean unregister(ICashMachine observer);
}
