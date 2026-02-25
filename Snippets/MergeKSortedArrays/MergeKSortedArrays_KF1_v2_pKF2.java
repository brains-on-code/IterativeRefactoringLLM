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
     * <p>Each inner array of {@code arrays} must be sorted in non-decreasing order.
     * The method uses a min-heap (priority queue) to repeatedly extract the
     * smallest current element among the arrays and build a fully merged,
     * sorted result.</p>
     *
     * @param arrays an array of sorted integer arrays
     * @return a single sorted array containing all elements from {@code arrays}
     */
    public static int[] method1(int[][] arrays) {
        // Each heap entry: [value, arrayIndex, elementIndex]
        PriorityQueue<int[]> minHeap =
            new PriorityQueue<>(Comparator.comparingInt(entry -> entry[0]));

        int totalLength = 0;

        // Seed the heap with the first element of each non-empty array
        for (int arrayIndex = 0; arrayIndex < arrays.length; arrayIndex++) {
            if (arrays[arrayIndex].length == 0) {
                continue;
            }
            minHeap.offer(new int[] {arrays[arrayIndex][0], arrayIndex, 0});
            totalLength += arrays[arrayIndex].length;
        }

        int[] merged = new int[totalLength];
        int mergedIndex = 0;

        // Repeatedly extract the smallest element and push the next from its array
        while (!minHeap.isEmpty()) {
            int[] entry = minHeap.poll();
            int value = entry[0];
            int arrayIndex = entry[1];
            int elementIndex = entry[2];

            merged[mergedIndex++] = value;

            int nextElementIndex = elementIndex + 1;
            if (nextElementIndex < arrays[arrayIndex].length) {
                minHeap.offer(
                    new int[] {arrays[arrayIndex][nextElementIndex], arrayIndex, nextElementIndex}
                );
            }
        }

        return merged;
    }
}