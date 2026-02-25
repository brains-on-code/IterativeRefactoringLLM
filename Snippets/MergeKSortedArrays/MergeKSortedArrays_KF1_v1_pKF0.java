package com.thealgorithms.datastructures.heaps;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Utility class for heap-based operations.
 */
public final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Merges multiple sorted integer arrays into a single sorted array.
     *
     * @param arrays an array of sorted int arrays
     * @return a single sorted array containing all elements from the input arrays
     */
    public static int[] method1(int[][] arrays) {
        // Min-heap storing {value, arrayIndex, elementIndex}
        PriorityQueue<int[]> minHeap = new PriorityQueue<>(Comparator.comparingInt(entry -> entry[0]));

        int totalLength = 0;
        for (int arrayIndex = 0; arrayIndex < arrays.length; arrayIndex++) {
            if (arrays[arrayIndex].length > 0) {
                minHeap.offer(new int[] {arrays[arrayIndex][0], arrayIndex, 0});
                totalLength += arrays[arrayIndex].length;
            }
        }

        int[] merged = new int[totalLength];
        int mergedIndex = 0;

        while (!minHeap.isEmpty()) {
            int[] current = minHeap.poll();
            int value = current[0];
            int arrayIndex = current[1];
            int elementIndex = current[2];

            merged[mergedIndex++] = value;

            int nextElementIndex = elementIndex + 1;
            if (nextElementIndex < arrays[arrayIndex].length) {
                minHeap.offer(new int[] {arrays[arrayIndex][nextElementIndex], arrayIndex, nextElementIndex});
            }
        }

        return merged;
    }
}