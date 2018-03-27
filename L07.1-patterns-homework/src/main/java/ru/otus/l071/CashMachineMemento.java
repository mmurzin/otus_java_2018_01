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
}
