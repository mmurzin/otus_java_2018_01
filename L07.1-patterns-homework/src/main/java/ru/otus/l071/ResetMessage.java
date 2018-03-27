package ru.otus.l071;


public class ResetMessage extends Message {

    private CashMachineMemento memento;

    public ResetMessage(CashMachineMemento memento) {
        super(Type.RESET);
        this.memento = memento;
    }

    public CashMachineMemento getMemento() {
        return memento;
    }
}
