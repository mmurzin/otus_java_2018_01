package ru.otus.l051.tests;


public abstract class Pet {

    public void tellAboutYourSelf() {
        System.out.println("Hello, I am "
                + getClass().getSimpleName()
                + ". My HasCode " + hashCode());
    }

    public abstract void sayHello1();

    public abstract void sayHello2();

    public abstract void sayHello3();

    public void sayBye() {
        System.out.println("Bye, I am "
                + getClass().getSimpleName()
                + ". My HasCode " + hashCode());
    }
}
