package ru.otus.l061;


import java.util.ArrayList;
import java.util.List;

public class App {

    public static void main(String[] args) {
        CashMachine atm = new CashMachine();
        System.out.println("CashMachine created");
        long requiredMoney = 3500;
        System.out.println("Balance: "
                + String.valueOf(atm.getBalance()));

        List<Note> insertNotes = getNoteList();
        System.out.println("Deposit money, value: "
                + Note.getBalance(insertNotes));
        atm.depositMoney(insertNotes);
        System.out.println("Balance: "
                + String.valueOf(atm.getBalance()));

        List<Note> items = atm.getMoney(requiredMoney);
        System.out.println("Attempt to get money, value " + requiredMoney);
        System.out.println("Success ? " + String.valueOf(!items.isEmpty()));
        System.out.println("Balance: "
                + String.valueOf(atm.getBalance()));

        requiredMoney = 1050;

        items = atm.getMoney(requiredMoney);
        System.out.println("Attempt to get money, value " + requiredMoney);
        System.out.println("Success ? " + String.valueOf(!items.isEmpty()));
        System.out.println("Balance: "
                + String.valueOf(atm.getBalance()));

    }

    private static List<Note> getNoteList() {
        List<Note> items = new ArrayList<>(6);
        items.add(new Note(Note.NOMINAL_100));
        items.add(new Note(Note.NOMINAL_200));
        items.add(new Note(Note.NOMINAL_500));
        items.add(new Note(Note.NOMINAL_1000));
        items.add(new Note(Note.NOMINAL_2000));
        items.add(new Note(Note.NOMINAL_5000));
        return items;
    }
}
