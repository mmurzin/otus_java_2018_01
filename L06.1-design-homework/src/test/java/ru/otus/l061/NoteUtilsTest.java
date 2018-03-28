package ru.otus.l061;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class NoteUtilsTest {

    @Test
    public void getBalance() {
        int SIZE = 10;
        List<Note> items = new ArrayList<>();
        Integer[] nominals = Note.getAvialableNominals();
        long balance = 0;
        for (int i = 0; i < SIZE; i++) {
            int index = new Random().nextInt(nominals.length);
            int nominal = nominals[index];
            balance += nominal;
            items.add(new Note(nominal));
        }
        assertEquals(balance,NoteUtils.countMoney(items));
    }
}
