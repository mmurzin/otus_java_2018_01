package ru.otus.l061;


import java.util.List;

public class NoteUtils {

    private NoteUtils() {
        throw new RuntimeException("Can not create NoteUtils instance");
    }

    public static long countMoney(List<Note> items) {
        long balance = 0;
        if (items == null || items.isEmpty()) {
            return balance;
        }
        for (Note item : items) {
            balance += item.getNominal();
        }
        return balance;
    }
}
