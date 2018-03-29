package ru.otus.l071;

import javafx.util.Pair;
import ru.otus.l071.interfaces.ICashMachine;
import ru.otus.l071.interfaces.IDepartment;

import java.util.*;

public class TestUtils {

    static List<Note> wrapToList(Note... notes) {
        List<Note> items = new ArrayList<>(notes.length);
        items.addAll(Arrays.asList(notes));
        return items;
    }

    static Note getRandomNote() {
        int[] nominals = Note.getAvialableNominals();
        return new Note(nominals[
                new Random().nextInt(nominals.length)]);
    }

    public static Pair<Long, List<Note>> getRandomNotes() {
        int size = TestUtils.getRandomSize();
        List<Note> notes = new ArrayList<>(size);
        Long balance = 0L;
        for (int i = 0; i < size; i++) {
            Note note = getRandomNote();
            balance += note.getNominal();
            notes.add(note);
        }
        return new Pair<>(balance, notes);
    }

    static Pair<Long, Map<Integer, List<Note>>> getRandomNotesMap(
            Pair<Long, List<Note>> randomPair) {
        Map<Integer, List<Note>> notesMap = new HashMap<>();
        for (Note note : randomPair.getValue()) {
            int nominal = note.getNominal();
            List<Note> items = notesMap.get(nominal);
            if (items == null) {
                items = new ArrayList<>();
            }
            items.add(note);
            notesMap.put(nominal, items);
        }
        return new Pair<>(randomPair.getKey(), notesMap);
    }

    static int getRandomSize(){
        final int MAX_SIZE = 1000;
        final int MIN_SIZE = 10;
        Random random = new Random();
        return random.nextInt(MAX_SIZE) + MIN_SIZE;
    }

}
