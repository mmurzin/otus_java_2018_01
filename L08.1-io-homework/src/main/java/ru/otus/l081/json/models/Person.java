package ru.otus.l081.json.models;

public class Person {
    private int age;
    private String name;

    public Person() {
        this.age = 12;
        this.name = "Alex";
    }

    public Person(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public int getAge() {
        return age;
    }
}
