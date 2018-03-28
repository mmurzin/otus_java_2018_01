package ru.otus.l061;


import org.junit.Test;

public class NoteTest {

    @Test(expected = IllegalArgumentException.class)
    public void createNotValidNote() {
        int badNominal = 128;
        new Note(badNominal);
    }
}
