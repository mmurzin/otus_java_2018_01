package ru.otus.l051.tests.dummyFolder;

import ru.otus.l051.annotations.After;
import ru.otus.l051.annotations.Before;
import ru.otus.l051.annotations.Test;
import ru.otus.l051.tests.Pet;

public class Dog extends Pet {

    @Before
    @Override
    public void tellAboutYourSelf() {
        super.tellAboutYourSelf();
    }

    @Test
    @Override
    public void sayHello2() {
        System.out.println("Dog say woof 2");
    }

    @Test
    @Override
    public void sayHello1() {
        System.out.println("Dog say woof 1");
    }

    @Test
    @Override
    public void sayHello3() {
        System.out.println("Dog say woof 3");
    }


    @After
    @Override
    public void sayBye() {
        super.sayBye();
    }
}
