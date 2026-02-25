package com.thealgorithms.datastructures.heaps;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Utility class for heap-based operations.
 */
public final class Class1 {

    private static final int VALUE_INDEX = 0;
    private static final int ARRAY_INDEX = 1;
    private static final int ELEMENT_INDEX = 2;

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
        if (arrays == null || arrays.length == 0) {
            return new int[0];
        }

        PriorityQueue<int[]> minHeap =
            new PriorityQueue<>(Comparator.comparingInt(entry -> entry[VALUE_INDEX]));

        int totalLength = 0;
        for (int arrayIndex = 0; arrayIndex < arrays.length; arrayIndex++) {
            int[] currentArray = arrays[arrayIndex];
            if (currentArray == null || currentArray.length == 0) {
                continue;
            }
            minHeap.offer(new int[] {currentArray[0], arrayIndex, 0});
            totalLength += currentArray.length;
        }

        int[] merged = new int[totalLength];
        int mergedIndex = 0;

        while (!minHeap.isEmpty()) {
            int[] current = minHeap.poll();
            int value = current[VALUE_INDEX];
            int arrayIndex = current[ARRAY_INDEX];
            int elementIndex = current[ELEMENT_INDEX];

            merged[mergedIndex++] = value;

            int nextElementIndex = elementIndex + 1;
            int[] sourceArray = arrays[arrayIndex];
            if (nextElementIndex < sourceArray.length) {
                minHeap.offer(
                    new int[] {sourceArray[nextElementIndex], arrayIndex, nextElementIndex}
                );
            }
        }

        return merged;
    }
}