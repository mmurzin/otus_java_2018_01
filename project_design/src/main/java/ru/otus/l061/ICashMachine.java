package ru.otus.l061;

import java.util.List;

public interface ICashMachine {
    void depositMoney(List<Bill> bills);

    List<Bill> getMoney(long value);

    long getBalance();

}
