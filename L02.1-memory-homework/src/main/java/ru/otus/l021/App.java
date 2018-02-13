package ru.otus.l021;


public class App {

    public static void main(String[] args) {

        InstrumentationApp.printObjectSize(new Object());
        InstrumentationApp.printObjectSize(new String());

        MemoryStand memoryStand = new MemoryStand();
        memoryStand.calculateObject(Object.class);
        memoryStand.calculateObject(String.class);
        memoryStand.calculateObjectCollection(0);
        memoryStand.calculateStringCollection(0);

        for (int i = 0; i < 100; i += 10) {
            memoryStand.calculateObjectCollection(i);
        }
        for (int i = 0; i < 100; i += 10) {
            memoryStand.calculateStringCollection(i);
        }
    }
}
