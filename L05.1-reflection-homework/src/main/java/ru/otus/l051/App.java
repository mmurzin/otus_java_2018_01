package ru.otus.l051;


import ru.otus.l051.annotations.After;
import ru.otus.l051.annotations.Before;
import ru.otus.l051.annotations.Test;

import java.lang.reflect.Method;

public class App {
    public static void main(String[] args) throws Exception {
        /*Person person = ReflectionHelper.instantiate(Person.class);
        System.out.println("person " + person);
        Person person1 = ReflectionHelper.instantiate(Person.class, "Emma");
        System.out.println("person1 " + person1);
        Person person2 = ReflectionHelper.instantiate(Person.class, 25);
        System.out.println("person2 " + person2);
        Person person3 = ReflectionHelper.instantiate(Person.class,
                "William", 12);
        System.out.println("person3 " + person3);
        Method[] methods = Person.class.getMethods();
        for (Method current : methods) {
            if (current.isAnnotationPresent(Before.class)) {
                current.invoke(person);
            }
            if (current.isAnnotationPresent(Test.class)) {
                current.invoke(person);
            }
            if (current.isAnnotationPresent(After.class)) {
                current.invoke(person);
            }
        }*/
        Tester.testClass("ru.otus.l051.Person");
    }
}
