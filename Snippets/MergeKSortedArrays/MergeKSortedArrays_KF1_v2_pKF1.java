package com.thealgorithms.datastructures.heaps;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Utility class for heap-based operations.
 */
public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    /**
     * Merges multiple sorted integer arrays into a single sorted array.
     *
     * @param arrays an array of sorted integer arrays
     * @return a single merged sorted array containing all elements
     */
    public static int[] mergeSortedArrays(int[][] arrays) {
        // Each heap element: {value, sourceArrayIndex, indexInSourceArray}
        PriorityQueue<int[]> minHeap =
                new PriorityQueue<>(Comparator.comparingInt(entry -> entry[0]));

        int mergedLength = 0;

        for (int sourceArrayIndex = 0; sourceArrayIndex < arrays.length; sourceArrayIndex++) {
            int[] currentArray = arrays[sourceArrayIndex];
            if (currentArray.length > 0) {
                minHeap.offer(new int[] {currentArray[0], sourceArrayIndex, 0});
                mergedLength += currentArray.length;
            }
        }

        int[] mergedArray = new int[mergedLength];
        int mergedIndex = 0;

        while (!minHeap.isEmpty()) {
            int[] heapEntry = minHeap.poll();
            int value = heapEntry[0];
            int sourceArrayIndex = heapEntry[1];
            int indexInSourceArray = heapEntry[2];

            mergedArray[mergedIndex++] = value;

            int nextIndexInSourceArray = indexInSourceArray + 1;
            int[] sourceArray = arrays[sourceArrayIndex];

            if (nextIndexInSourceArray < sourceArray.length) {
                minHeap.offer(
                        new int[] {sourceArray[nextIndexInSourceArray], sourceArrayIndex, nextIndexInSourceArray});
            }
        }

        return mergedArray;
    }
}