package ru.otus.l051.tests;


import ru.otus.l051.annotations.After;
import ru.otus.l051.annotations.Before;
import ru.otus.l051.annotations.Test;

public class Cat extends Pet {

    @Before
    @Override
    public void tellAboutYourSelf() {
        super.tellAboutYourSelf();
    }

    @Test
    @Override
    public void sayHello2() {
        System.out.println("Cat say meow 2");
    }

    @Test
    @Override
    public void sayHello1() {
        System.out.println("Cat say meow 1");
    }

    @Test
    @Override
    public void sayHello3() {
        System.out.println("Cat say meow 3");
    }


    @After
    @Override
    public void sayBye() {
        super.sayBye();
    }
}
