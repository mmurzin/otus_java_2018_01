package ru.otus.l061;


import java.util.ArrayList;
import java.util.List;

public class App {

    public static void main(String[] args) {
        CashMachine atm = new CashMachine();
        atm.depositMoney(getMoney());
        atm.printDump();
    }

    private static List<Bill> getMoney() {
        List<Bill> items = new ArrayList<>(6);
        items.add(new Bill(Bill.NOMINAL_100));
        items.add(new Bill(Bill.NOMINAL_200));
        items.add(new Bill(Bill.NOMINAL_500));
        items.add(new Bill(Bill.NOMINAL_1000));
        items.add(new Bill(Bill.NOMINAL_2000));
        items.add(new Bill(Bill.NOMINAL_5000));
        return items;
    }
}
