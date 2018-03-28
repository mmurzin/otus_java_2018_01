package ru.otus.l071;

import java.util.List;
import java.util.Map;

public class CashMachineMemento {
    private final Map<Integer, List<Note>> machineState;

    public CashMachineMemento(Map<Integer, List<Note>> machineState) {
        this.machineState = machineState;
    }

    public Map<Integer, List<Note>> getSavedState() {
        return machineState;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof CashMachineMemento) {
            CashMachineMemento memento =
                    (CashMachineMemento) obj;
            if (memento.machineState == null ||
                    this.machineState == null) {
                return false;
            }
            if (memento.machineState.size() != this.machineState.size()) {
                return false;
            }

            for (Map.Entry<Integer, List<Note>> entry
                    : machineState.entrySet()) {
                int currentNominal = entry.getKey();
                int currentNotesCount =
                        getNotesCount(currentNominal, entry.getValue());
                int mementoNotesCount =
                        getNotesCount(currentNominal,
                                memento.machineState.get(currentNominal));
                if (currentNotesCount != mementoNotesCount) {
                    return false;
                }
            }
            return true;
        }
        return false;

    }

    private int getNotesCount(Integer nominal, List<Note> items) {
        if (items == null || items.size() == 0) {
            return 0;
        }
        int count = 0;
        for (Note current :
                items) {
            if (current.getNominal() == nominal) {
                count++;
            }
        }
        return count;
    }
}
