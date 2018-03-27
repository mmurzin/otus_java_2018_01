package ru.otus.l061;

import javafx.util.Pair;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CashMachineTest {
    private CashMachine cashMachine;
    private Integer[] nominals;

    @Before
    public void setUp() {
        cashMachine = new CashMachine();
        nominals = Note.getAvialableNominals();
    }

    @Test
    public void depositMoney() {
        long balance = 0;
        Assert.assertEquals(balance, cashMachine.getBalance());

        Note note = getRandomNote();
        cashMachine.depositMoney(wrapToList(note));
        balance += note.getNominal();
        Assert.assertEquals(balance, cashMachine.getBalance());

        Pair<Long, List<Note>> randomNotePair = getRandomNotes();
        cashMachine.depositMoney(randomNotePair.getValue());
        balance += randomNotePair.getKey();
        Assert.assertEquals(balance, cashMachine.getBalance());

    }

    @Test
    public void getMoney() {
        Pair<Long, List<Note>> randomNotePair = getRandomNotes();
        List<Note> items = randomNotePair.getValue();
        cashMachine.depositMoney(items);
        int noteCount = items.size() / 2;
        long needBalance = 0;
        for (int i = 0; i < noteCount; i++) {
            needBalance += items.get(i).getNominal();
        }
        List<Note> userNotes = cashMachine.getMoney(needBalance);
        Assert.assertEquals(userNotes.isEmpty(), false);

        long notValidBalance = 1050;
        userNotes = cashMachine.getMoney(notValidBalance);
        Assert.assertEquals(userNotes.isEmpty(), true);

    }

    private List<Note> wrapToList(Note... notes) {
        List<Note> items = new ArrayList<>(notes.length);
        items.addAll(Arrays.asList(notes));
        return items;
    }

    private Note getRandomNote() {
        return new Note(nominals[
                new Random().nextInt(nominals.length)]);
    }

    private Pair<Long, List<Note>> getRandomNotes() {
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

}
