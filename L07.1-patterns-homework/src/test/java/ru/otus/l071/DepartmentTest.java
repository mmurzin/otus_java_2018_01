package ru.otus.l071;

import javafx.util.Pair;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.otus.l071.interfaces.ICashMachine;
import ru.otus.l071.interfaces.IDepartment;

import java.util.List;

public class DepartmentTest {

    private IDepartment department;

    @Before
    public void setUp() {
        department = new Department();
    }

    @Test
    public void getBalances() {
        int size = TestUtils.getRandomSize();
        long balance = 0;
        for (int i = 0; i < size; i++) {
            Pair<Long, List<Note>> notesPair = TestUtils.getRandomNotes();
            balance += notesPair.getKey();
            ICashMachine machine = new CashMachine(department);
            machine.depositMoney(notesPair.getValue());
        }
        Assert.assertEquals(balance, department.getBalances());
    }

    @Test
    public void resetMachines() {
        int size = TestUtils.getRandomSize();
        for (int i = 0; i < size; i++) {
            Pair<Long, List<Note>> notesPair = TestUtils.getRandomNotes();
            ICashMachine machine = new CashMachine(department);
            machine.depositMoney(notesPair.getValue());
        }
        Assert.assertNotEquals(department.getBalances(), 0);
        department.resetMachines();
        Assert.assertEquals(department.getBalances(), 0);
    }

    @Test(expected = NullSubjectException.class)
    public void register() {
        department.register(null);
    }

    @Test
    public void unregister() {
        ICashMachine machine = new CashMachine(department);
        department.register(machine);
        Assert.assertEquals(department.unregister(machine), true);
        Assert.assertEquals(department.unregister(null), false);
    }
}
