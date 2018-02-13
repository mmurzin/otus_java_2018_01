package ru.otus.l021;


public class MemoryStand {

    private Runtime runtime;

    MemoryStand() {
        runtime = Runtime.getRuntime();
    }

    public void calculateObject(Class clazz) {
        long memoryPrev = runtime.totalMemory() - runtime.freeMemory();
        try {
            System.gc();
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            int size = 20_000_000;
            Object[] array = new Object[size];
            for (int i = 0; i < size; i++) {
                array[i] = clazz.newInstance();
            }
            long allocatedMemory = (runtime.totalMemory() - runtime.freeMemory()) - memoryPrev;
            System.out.println(clazz.getSimpleName() + " " + (allocatedMemory / size) + " bytes");
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

        try {
            System.gc();
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void calculateObjectCollection(int size) {
        long prevMemory = runtime.totalMemory() - runtime.freeMemory();
        Object[] arr = new Object[size];
        long currentMemory = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Object[" + size + "] - "
                + (currentMemory - prevMemory) + " bytes");
        System.gc();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void calculateStringCollection(int size) {
        long prevMemory = runtime.totalMemory() - runtime.freeMemory();
        String[] arr = new String[size];
        long currentMemory = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("String[" + size + "] - "
                + (currentMemory - prevMemory) + " bytes");
        System.gc();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
