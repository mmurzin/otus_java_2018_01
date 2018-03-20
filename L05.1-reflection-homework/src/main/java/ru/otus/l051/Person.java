package ru.otus.l051;

import ru.otus.l051.annotations.After;
import ru.otus.l051.annotations.Before;
import ru.otus.l051.annotations.Test;

public class Person {

    static final int DEFAULT_AGE = 16;
    static final String DEFAULT_NAME = "Alex";

    private String name = DEFAULT_NAME;
    private int age = DEFAULT_AGE;

    public Person() {
        this(DEFAULT_NAME, DEFAULT_AGE);
    }

    public Person(String name) {
        this(name, DEFAULT_AGE);
    }

    public Person(Integer age) {
        this(DEFAULT_NAME, age);
    }

    Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    @After
    public void afterMethod() {
        System.out.println("Person " + hashCode() + " afterMethod called");
    }

    @Test
    public void testMethod1() {
        System.out.println("testMethod1 called");
    }

    @Override
    public String toString() {
        return "Name: " + this.name + ", Age: " + this.age;
    }

    @Before
    public void beforeMethod() {
        System.out.println("Person " + hashCode() + " beforeMethod called");
    }

    @Test
    public void testMethod3() {
        System.out.println("testMethod3 called");
    }

    @Test
    public void testMethod2() {
        System.out.println("testMethod2 called");
    }
}
