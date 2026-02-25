package com.thealgorithms.datastructures.heaps;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Utility class for heap-based operations.
 */
public final class HeapUtils {

    private HeapUtils() {
        // Utility class; prevent instantiation
    }

    /**
     * Merges multiple sorted integer arrays into a single sorted array.
     *
     * @param arrays an array of sorted integer arrays
     * @return a single merged sorted array containing all elements
     */
    public static int[] mergeSortedArrays(int[][] arrays) {
        // Each heap element: {value, arrayIndex, elementIndex}
        PriorityQueue<int[]> minHeap =
                new PriorityQueue<>(Comparator.comparingInt(entry -> entry[0]));

        int totalElementCount = 0;

        for (int arrayIndex = 0; arrayIndex < arrays.length; arrayIndex++) {
            int[] currentArray = arrays[arrayIndex];
            if (currentArray.length > 0) {
                minHeap.offer(new int[] {currentArray[0], arrayIndex, 0});
                totalElementCount += currentArray.length;
            }
        }

        int[] mergedArray = new int[totalElementCount];
        int mergedArrayIndex = 0;

        while (!minHeap.isEmpty()) {
            int[] heapEntry = minHeap.poll();
            int value = heapEntry[0];
            int arrayIndex = heapEntry[1];
            int elementIndex = heapEntry[2];

            mergedArray[mergedArrayIndex++] = value;

            int nextElementIndex = elementIndex + 1;
            int[] sourceArray = arrays[arrayIndex];

            if (nextElementIndex < sourceArray.length) {
                minHeap.offer(
                        new int[] {sourceArray[nextElementIndex], arrayIndex, nextElementIndex});
            }
        }

        return mergedArray;
    }
}