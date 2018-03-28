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
                + NoteUtils.countMoney(insertNotes));
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
        items.add(new Note(Note.NominalEnum.NOMINAL_100.getNominal()));
        items.add(new Note(Note.NominalEnum.NOMINAL_200.getNominal()));
        items.add(new Note(Note.NominalEnum.NOMINAL_500.getNominal()));
        items.add(new Note(Note.NominalEnum.NOMINAL_1000.getNominal()));
        items.add(new Note(Note.NominalEnum.NOMINAL_2000.getNominal()));
        items.add(new Note(Note.NominalEnum.NOMINAL_5000.getNominal()));
        return items;
    }
}
