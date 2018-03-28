package ru.otus.l071;

import javafx.util.Pair;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.otus.l071.interfaces.ICashMachine;

import java.util.*;

public class CashMachineTest {
    private ICashMachine cashMachine;

    @Before
    public void setUp() {
        cashMachine = new CashMachine(new Department());
    }

    @Test
    public void depositMoney() {
        long balance = 0;
        Assert.assertEquals(balance, cashMachine.getBalance());

        Note note = TestUtils.getRandomNote();
        cashMachine.depositMoney(TestUtils.wrapToList(note));
        balance += note.getNominal();
        Assert.assertEquals(balance, cashMachine.getBalance());

        Pair<Long, List<Note>> randomNotePair = TestUtils.getRandomNotes();
        cashMachine.depositMoney(randomNotePair.getValue());
        balance += randomNotePair.getKey();
        Assert.assertEquals(balance, cashMachine.getBalance());

    }

    @Test
    public void getMoney() {
        Pair<Long, List<Note>> randomNotePair = TestUtils.getRandomNotes();
        List<Note> items = randomNotePair.getValue();
        cashMachine.depositMoney(items);
        int noteCount = items.size() / 2;
        long needBalance = 0;
        for (int i = 0; i < noteCount; i++) {
            needBalance += items.get(i).getNominal();
        }
        List<Note> userNotes = cashMachine.getMoney(needBalance);
        Assert.assertEquals(userNotes.isEmpty(), false);

        long notValidBalance = 1050;
        userNotes = cashMachine.getMoney(notValidBalance);
        Assert.assertEquals(userNotes.isEmpty(), true);

    }

    @Test
    public void getBalance() {
        Pair<Long, List<Note>> randomNotePair = TestUtils.getRandomNotes();
        long pairBalance = randomNotePair.getKey();
        cashMachine.depositMoney(randomNotePair.getValue());
        Assert.assertEquals(pairBalance, cashMachine.getBalance());
    }

    @Test
    public void notifyMessage() {
        CashMachineMemento initialMemento = cashMachine.saveToMemento();
        cashMachine.depositMoney(TestUtils.getRandomNotes().getValue());
        cashMachine.notifyMessage(new ResetMessage(initialMemento));
        Assert.assertEquals(cashMachine.getBalance(), 0);
    }

    @Test
    public void saveToMemento() {
        Pair<Long, List<Note>> randomNotes = TestUtils.getRandomNotes();
        Pair<Long, Map<Integer, List<Note>>> mapPair = TestUtils.getRandomNotesMap(randomNotes);
        CashMachineMemento memento = new CashMachineMemento(mapPair.getValue());
        cashMachine.depositMoney(randomNotes.getValue());

        Assert.assertEquals(memento, cashMachine.saveToMemento());
    }

    @Test
    public void restoreFromMemento() {
        Pair<Long, List<Note>> randomNotes = TestUtils.getRandomNotes();
        Pair<Long, Map<Integer, List<Note>>> mapPair = TestUtils.getRandomNotesMap(randomNotes);
        CashMachineMemento memento = new CashMachineMemento(mapPair.getValue());
        cashMachine.restoreFromMemento(memento);

        Assert.assertEquals(cashMachine.saveToMemento(),memento);
        Assert.assertEquals((long)randomNotes.getKey(),cashMachine.getBalance());
    }
}
