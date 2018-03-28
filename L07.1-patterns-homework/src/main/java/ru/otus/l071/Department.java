package ru.otus.l071;


import ru.otus.l071.interfaces.ICashMachine;
import ru.otus.l071.interfaces.IDepartment;

import java.util.*;

public class Department implements IDepartment {
    private final Set<ICashMachine> cashMachines = new HashSet<>();

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
        /*По сути делает то же самое, на данном этапе,
            можно обойтись без паттерна observer
        cashMachines
                .forEach(it -> it.restoreFromMemento(cashMachineStates.get(it)));*/

        cashMachines.forEach(it ->
                it.notifyMessage(new ResetMessage(cashMachineStates.get(it))));
    }

    @Override
    public void register(ICashMachine machine) {
        if(machine == null){
            throw new NullSubjectException();
        }
        cashMachineStates.put(machine, machine.saveToMemento());
        cashMachines.add(machine);
    }

    @Override
    public boolean unregister(ICashMachine machine) {
        if(machine != null){
            cashMachineStates.remove(machine);
            cashMachines.remove(machine);
            return true;
        }
        return false;
    }
}
