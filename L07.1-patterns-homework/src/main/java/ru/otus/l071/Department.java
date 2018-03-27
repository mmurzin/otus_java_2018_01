package ru.otus.l071;


import ru.otus.l071.interfaces.ICashMachine;
import ru.otus.l071.interfaces.IDepartment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Department implements IDepartment {
    private final List<ICashMachine> cashMachines = new ArrayList<>();

    private final Map<ICashMachine, CashMachineMemento> cashMachineStates
            = new HashMap<>();

    @Override
    public long getBalances() {
        return cashMachines
                .stream()
                .mapToLong(ICashMachine::getBalance)
                .sum();
    }

    @Override
    public void resetMachines() {
        /*По сути делает то же самое, можно обойтись без паттерна observer вообще
        cashMachines
                .forEach(it -> it.restoreFromMemento(cashMachineStates.get(it)));*/

        cashMachines.forEach(it ->
                it.notify(new ResetMessage(cashMachineStates.get(it))));
    }

    @Override
    public void register(ICashMachine machine) {
        cashMachineStates.put(machine, machine.saveToMemento());
        cashMachines.add(machine);
    }

    @Override
    public void unregister(ICashMachine machine) {
        cashMachineStates.remove(machine);
        cashMachines.remove(machine);
    }
}
