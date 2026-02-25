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
     * <p>This method assumes each inner array of {@code arrays} is individually
     * sorted in non-decreasing order. It uses a min-heap (priority queue) to
     * repeatedly extract the smallest current element among the arrays and
     * build a fully merged, sorted result.</p>
     *
     * @param arrays an array of sorted integer arrays
     * @return a single sorted array containing all elements from {@code arrays}
     */
    public static int[] method1(int[][] arrays) {
        // Min-heap storing entries of the form [value, arrayIndex, elementIndex]
        PriorityQueue<int[]> minHeap = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));

        int totalLength = 0;

        // Initialize the heap with the first element of each non-empty array
        for (int arrayIndex = 0; arrayIndex < arrays.length; arrayIndex++) {
            if (arrays[arrayIndex].length > 0) {
                minHeap.offer(new int[] {arrays[arrayIndex][0], arrayIndex, 0});
                totalLength += arrays[arrayIndex].length;
            }
        }

        int[] merged = new int[totalLength];
        int mergedIndex = 0;

        // Extract the smallest element and push the next element from the same array
        while (!minHeap.isEmpty()) {
            int[] entry = minHeap.poll();
            int value = entry[0];
            int arrayIndex = entry[1];
            int elementIndex = entry[2];

            merged[mergedIndex++] = value;

            int nextElementIndex = elementIndex + 1;
            if (nextElementIndex < arrays[arrayIndex].length) {
                minHeap.offer(new int[] {arrays[arrayIndex][nextElementIndex], arrayIndex, nextElementIndex});
            }
        }

        return merged;
    }
}