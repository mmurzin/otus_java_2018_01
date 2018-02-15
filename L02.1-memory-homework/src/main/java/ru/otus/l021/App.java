package ru.otus.l021;


public class App {
    private static final int MAX_COLLECTIONS_SIZE = 100;
    public static void main(String[] args) {

        InstrumentationApp.printObjectSize(new Object());
        InstrumentationApp.printObjectSize(new String());

        MemoryStand memoryStand = new MemoryStand();

        memoryStand.calculateObject(Object.class);

        memoryStand.calculateString(false);
        memoryStand.calculateString(true);

        memoryStand.calculateObjectCollection(0);
        memoryStand.calculateStringCollection(0);


        for (int i = 0; i < MAX_COLLECTIONS_SIZE; i += 10) {
            memoryStand.calculateObjectCollection(i);
        }
        for (int i = 0; i < MAX_COLLECTIONS_SIZE; i += 10) {
            memoryStand.calculateStringCollection(i);
        }
    }
}
