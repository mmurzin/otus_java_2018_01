package ru.otus.l021;


public class MemoryStand {

    private Runtime runtime;
    private static final int SIZE = 20_000_000;

    MemoryStand() {
        runtime = Runtime.getRuntime();
    }

    public void calculateObject(Class clazz) {
        if (clazz.getSimpleName().equals(String.class.getSimpleName())) {
            System.out.println("Use calculateString method");
            return;
        }
        Object[] array = new Object[SIZE];
        System.gc();
        long memoryPrev = getMemory();
        try {
            for (int i = 0; i < SIZE; i++) {
                array[i] = clazz.newInstance();

            }
            long allocatedMemory = getMemory() - memoryPrev;
            System.gc();
            System.out.println(clazz.getSimpleName() + " " + (allocatedMemory / SIZE) + " bytes");
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    public void calculateString(boolean isUsePool) {
        Object[] array = new Object[SIZE];
        System.gc();
        long memoryPrev = getMemory();
        for (int i = 0; i < SIZE; i++) {
            if (isUsePool) {
                array[i] = "";
            } else {
                array[i] = new String(new char[0]);
            }


        }
        long allocatedMemory = getMemory() - memoryPrev;
        System.gc();
        System.out.println("String pool used: " + isUsePool);
        System.out.println("String " + (allocatedMemory / SIZE) + " bytes");
    }

    public void calculateObjectCollection(int size) {
        System.gc();
        long prevMemory = getMemory();
        Object[] arr = new Object[size];
        long currentMemory = getMemory();
        System.gc();
        System.out.println("Object[" + size + "] : "
                + (currentMemory - prevMemory) + " bytes");
    }

    public void calculateStringCollection(int size) {
        System.gc();
        long prevMemory = getMemory();
        String[] arr = new String[size];
        long currentMemory = getMemory();
        System.gc();
        System.out.println("String[" + size + "] : "
                + (currentMemory - prevMemory) + " bytes");
    }

    private long getMemory() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return runtime.totalMemory() - runtime.freeMemory();
    }
}
