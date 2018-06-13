package ru.otus.l0141;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class App {
    public static void main(String[] args) {
        final int ARRAY_SIZE = 10;
        final byte THREAD_COUNT = 4;

        List<Integer> list = new ArrayList<>(ARRAY_SIZE);
        for (int i = 0; i < ARRAY_SIZE; i++) {
            list.add(i);
        }

        Collections.shuffle(list);

        ParallelSorter sorter = new ParallelSorter();
        Integer[] array = list.toArray(new Integer[0]);
        System.out.println(Arrays.toString(array));
        sorter.sort(array);
        System.out.println(Arrays.toString(array));
    }
}
