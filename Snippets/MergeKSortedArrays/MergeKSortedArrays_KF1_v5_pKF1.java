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
     * @param sortedArrays an array of sorted integer arrays
     * @return a single merged sorted array containing all elements
     */
    public static int[] mergeSortedArrays(int[][] sortedArrays) {
        // Each heap element: {value, arrayIndex, elementIndex}
        PriorityQueue<int[]> minHeap =
                new PriorityQueue<>(Comparator.comparingInt(heapElement -> heapElement[0]));

        int totalElementCount = 0;

        for (int arrayIndex = 0; arrayIndex < sortedArrays.length; arrayIndex++) {
            int[] currentArray = sortedArrays[arrayIndex];
            if (currentArray.length > 0) {
                minHeap.offer(new int[] {currentArray[0], arrayIndex, 0});
                totalElementCount += currentArray.length;
            }
        }

        int[] mergedArray = new int[totalElementCount];
        int mergedArrayIndex = 0;

        while (!minHeap.isEmpty()) {
            int[] heapElement = minHeap.poll();
            int value = heapElement[0];
            int arrayIndex = heapElement[1];
            int elementIndex = heapElement[2];

            mergedArray[mergedArrayIndex++] = value;

            int nextElementIndex = elementIndex + 1;
            int[] currentArray = sortedArrays[arrayIndex];

            if (nextElementIndex < currentArray.length) {
                minHeap.offer(
                        new int[] {
                            currentArray[nextElementIndex],
                            arrayIndex,
                            nextElementIndex
                        });
            }
        }

        return mergedArray;
    }
}