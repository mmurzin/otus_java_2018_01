package ru.otus.l071;

import javafx.util.Pair;
import ru.otus.l071.interfaces.ICashMachine;
import ru.otus.l071.interfaces.IDepartment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class App {
    private static Integer[] nominals;

    public static void main(String[] args) {
        nominals = Note.getAvialableNominals();
        IDepartment department = new Department();
        long balance = 0;
        for (int i = 0; i < 10; i++) {
            ICashMachine machine = new CashMachine(department);

            Pair<Long, List<Note>> listPair = getRandomNotes();
            balance += listPair.getKey();

            machine.depositMoney(listPair.getValue());
        }
        System.out.println("In for balance " + balance);
        System.out.println("Department balance " + department.getBalances());
        System.out.println("Reset all machines");
        department.resetMachines();
        System.out.println("Department balance " + department.getBalances());
    }

    private static Pair<Long, List<Note>> getRandomNotes() {
        final int MAX_SIZE = 1000;
        final int MIN_SIZE = 10;
        Random random = new Random();
        int size = random.nextInt(MAX_SIZE) + MIN_SIZE;
        List<Note> notes = new ArrayList<>(size);
        Long balance = 0L;
        for (int i = 0; i < size; i++) {
            Note note = getRandomNote();
            balance += note.getNominal();
            notes.add(note);
        }
        return new Pair<>(balance, notes);
    }

    private static Note getRandomNote() {
        return new Note(nominals[
                new Random().nextInt(nominals.length)]);
    }
}
