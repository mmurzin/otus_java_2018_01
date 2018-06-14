package ru.otus.l0141;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParallelSorter {
    private final int threadCount;
    private final int DEFAULT_THREAD_COUNT = 4;

    public ParallelSorter() {
        threadCount = DEFAULT_THREAD_COUNT;
    }

    public ParallelSorter(int count) {
        threadCount = count;
    }

    void sort(Integer[] array) throws InterruptedException {
        int chunkSize = array.length / threadCount;
        List<Chunk> chunks = new ArrayList<>(threadCount);
        for (int i = 0; i < threadCount; i++) {
            chunks.add(new Chunk(i * chunkSize, (i + 1) * chunkSize));

        }
        chunks.get(threadCount - 1).to = array.length;

        for (Chunk chunk : chunks) {
            Thread thread = new Thread(() -> Arrays.sort(array, chunk.from, chunk.to));
            thread.start();
            thread.join();
        }
        sortChunks(chunks, array);
    }

    private void sortChunks(List<Chunk> chunks, Integer[] array) throws InterruptedException {
        List<Chunk> chunkList = new ArrayList<>();
        for (int i = 0; i < chunks.size() / 2; i++) {
            Chunk left = chunks.get(i * 2);
            Chunk right = chunks.get(i * 2 + 1);
            chunkList.add(new Chunk(left.from, right.to));
            Thread thread = new Thread(() -> mergeChunks(array, left, right));
            thread.start();
            thread.join();
        }

        if (chunks.size() % 2 != 0) {
            chunkList.add(chunks.get(chunks.size() - 1));
        }
        if(chunkList.size() > 1){
            sortChunks(chunkList, array);
        }
    }

    private void mergeChunks(Integer[] array, Chunk left, Chunk right) {
        int leftIndex = left.from;
        int rightIndex = right.from;

        Integer[] temp = new Integer[right.to - left.from];

        for (int i = 0; i < temp.length; i++) {

            if (left.to == leftIndex) {
                System.arraycopy(array, rightIndex, temp, i, temp.length - i);
                break;
            }

            if (right.to == rightIndex) {
                System.arraycopy(array, leftIndex, temp, i, temp.length - i);
                break;
            }

            temp[i] = Integer.compare(array[leftIndex], array[rightIndex]) < 0 ? array[leftIndex++] : array[rightIndex++];
        }
        System.arraycopy(temp, 0, array, left.from, temp.length);
    }


    class Chunk {
        private final int from;
        private int to;

        Chunk(int from, int to) {
            this.from = from;
            this.to = to;
        }
    }
}
