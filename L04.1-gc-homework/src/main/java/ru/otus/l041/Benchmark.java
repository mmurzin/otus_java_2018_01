package ru.otus.l041;

import java.util.ArrayList;

public class Benchmark implements BenchmarkMBean {
    private static final int DEFAULT_SIZE = 15;
    private volatile int size = DEFAULT_SIZE;
    private static ArrayList<String> arrayList = new ArrayList<>();


    @SuppressWarnings("InfiniteLoopStatement")
    void run() throws Exception {
        while (true) {
            int localSize = size;
            for (int i = 0; i < localSize; i++) {
                arrayList.add(String.valueOf(new char[0]));
            }
            arrayList.remove(arrayList.size() - 1);
            Thread.sleep(0, 1);
        }
    }

    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public void setSize(int size) {
        this.size = size;
    }
}
