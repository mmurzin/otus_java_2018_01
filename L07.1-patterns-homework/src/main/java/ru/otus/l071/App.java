package ru.otus.l071;

import ru.otus.l071.interfaces.ICashMachine;
import ru.otus.l071.interfaces.IDepartment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class App {

    public static void main(String[] args) {
        int machinesCount = 10;
        IDepartment department = new Department();
        long initialBalance = 0;

        List<Integer> noteItems = new ArrayList<>(machinesCount);
        List<ICashMachine> cashMachines = new ArrayList<>(machinesCount);
        for (int i = 0; i < machinesCount; i++) {
            ICashMachine machine = new CashMachine(department);
            List<Note> notes = getRandomNotes();
            machine.depositMoney(notes);
            machine.saveMachineState();
            initialBalance += machine.getBalance();

            cashMachines.add(machine);
            noteItems.add(notes.get(0).getNominal());
        }

        System.out.println("Deposit in all machines " + initialBalance);
        System.out.println("Deposit in department " + department.getBalances());
        System.out.println("Saved balance " + initialBalance);
        long deltaNotes = 0;
        for (int i = 0; i < machinesCount; i++) {
            int noteNominal = noteItems.get(i);
            List<Note> notes = cashMachines
                    .get(i)
                    .getMoney(noteNominal);
            if (!notes.isEmpty()) {
                deltaNotes += noteNominal;
            }
        }
        System.out.println("Received money from all machines " + deltaNotes);
        System.out.println("Department balance " + department.getBalances());



        System.out.println("Reset all machines");
        department.resetMachines();
        System.out.println("Department balance after reset " + department.getBalances());
    }


    private static List<Note> getRandomNotes() {
        final int MAX_SIZE = 1000;
        final int MIN_SIZE = 10;
        Random random = new Random();
        int size = random.nextInt(MAX_SIZE) + MIN_SIZE;
        List<Note> notes = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            Note note = getRandomNote();
            notes.add(note);
        }
        return notes;
    }

    private static Note getRandomNote() {
        int[] nominals = Note.getAvialableNominals();
        return new Note(nominals[
                new Random().nextInt(nominals.length)]);
    }

}
