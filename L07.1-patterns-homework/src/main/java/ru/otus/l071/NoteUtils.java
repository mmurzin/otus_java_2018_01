package ru.otus.l071;

import java.util.List;

public class NoteUtils {

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
