package ru.otus.l0141;

import java.util.Arrays;

public class ParallelSorter {
    private final int threadCount;
    private final int DEFAULT_THREAD_COUNT = 4;

    public ParallelSorter() {
        threadCount = DEFAULT_THREAD_COUNT;
    }

    private ParallelSorter(int count) {
        threadCount = count;
    }

    void sort(Integer[] array) {
        Arrays.sort(array);
    }



}
