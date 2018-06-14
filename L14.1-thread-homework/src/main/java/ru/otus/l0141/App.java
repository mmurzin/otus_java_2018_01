package ru.otus.l0141;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class App {
    public static void main(String[] args) throws InterruptedException {
        final int ARRAY_SIZE = 1_000_000;
        List<Integer> list = new ArrayList<>(ARRAY_SIZE);
        for (int i = 0; i < ARRAY_SIZE; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        
        ParallelSorter sorter = new ParallelSorter(4);
        Integer[] array = list.toArray(new Integer[0]);
        Integer[] copyArray = Arrays.copyOfRange(array, 0, array.length);

        sorter.sort(array);
        Arrays.sort(copyArray);

        boolean isCorrect = true;
        for (int i = 0; i < array.length; i++) {
            if (!array[i].equals(copyArray[i])) {
                isCorrect = false;
                break;
            }
        }
        if(isCorrect){
            System.out.println("Sorting is correct");
        }else{
            System.out.println("Wrong sort");
        }
    }
}
