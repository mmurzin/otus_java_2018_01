package ru.otus.l071;

import ru.otus.l071.interfaces.ICashMachine;
import ru.otus.l071.interfaces.IDepartment;

import java.util.*;

public class CashMachine implements ICashMachine {

    private Map<Integer, List<Note>> notesMap = new TreeMap<>();

    public CashMachine(IDepartment department) {
        department.register(this);
    }

    @Override
    public void depositMoney(List<Note> notes) {
        for (Note note : notes) {
            insertNote(note);
        }
    }

    private void insertNote(Note note) {
        int nominal = note.getNominal();
        List<Note> items = notesMap.get(nominal);
        if (items == null) {
            items = new ArrayList<>();
        }
        items.add(note);
        notesMap.put(nominal, items);
    }

    @Override
    public List<Note> getMoney(long value) {
        if (value > getBalance()) {
            return null;
        }

        Map<Integer,List<Note>> backup = new TreeMap<>(notesMap);

        List<Integer> avialableNominals = getAvialableNominals();

        ArrayList<Note> userNotes = new ArrayList<>();
        long delta = value;
        boolean isDeltaChanged;

        while (true) {
            isDeltaChanged = false;
            for (Integer nominal : avialableNominals) {


                if (nominal > delta) {
                    continue;
                }

                List<Note> atmItems = notesMap.get(nominal);
                if (atmItems == null || atmItems.isEmpty()) {
                    notesMap.remove(nominal);
                    continue;
                }

                ArrayList<Note> items = new ArrayList<>(atmItems);
                ListIterator<Note> iterator = items.listIterator();
                boolean isNeedUpdateMap = false;

                while (iterator.hasNext()) {
                    Note noteItem = iterator.next();
                    if (noteItem.getNominal() <= delta) {
                        isNeedUpdateMap = true;
                        isDeltaChanged = true;

                        userNotes.add(noteItem);

                        delta -= nominal;
                        iterator.remove();
                    }
                }

                if (isNeedUpdateMap) {
                    updateNotesMap(nominal, items);
                }
            }

            if (!isDeltaChanged) {
                break;
            }
        }
        if (delta != 0) {
            this.notesMap = backup;
            userNotes = new ArrayList<>();
        }
        return userNotes;
    }

    private void updateNotesMap(Integer nominal, ArrayList<Note> items) {
        if (items.isEmpty()) {
            notesMap.remove(nominal);
        } else {
            notesMap.put(nominal, items);
        }
    }

    private List<Integer> getAvialableNominals() {
        Set<Integer> nominalsSet = notesMap.keySet();
        List<Integer> nominals = new ArrayList<>(nominalsSet);
        Collections.reverse(nominals);
        return nominals;
    }

    @Override
    public long getBalance() {
        long balance = 0;
        for (Map.Entry<Integer, List<Note>> entry : notesMap.entrySet()) {
            balance += entry
                    .getValue()
                    .stream()
                    .mapToLong(Note::getNominal).sum();
        }
        return balance;
    }

    @Override
    public void notifyMessage(Message message) {
        switch (message.getType()) {
            case RESET:
                restoreFromMemento(((ResetMessage) message).getMemento());
                break;
        }
    }

    @Override
    public CashMachineMemento saveToMemento() {
        return new CashMachineMemento(new HashMap<>(notesMap));
    }

    @Override
    public void restoreFromMemento(CashMachineMemento memento) {
        this.notesMap = memento.getSavedState();
    }
}
