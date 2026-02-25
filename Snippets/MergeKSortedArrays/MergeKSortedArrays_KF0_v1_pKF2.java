package com.thealgorithms.datastructures.heaps;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Utility class for merging multiple sorted arrays into a single sorted array.
 *
 * <p>Uses a min-heap to always extract the smallest current element among the arrays.
 *
 * <p>Time Complexity: O(n log k), where n is the total number of elements and k is the number of
 * arrays.
 *
 * <p>Space Complexity: O(k) for the heap.
 */
public final class MergeKSortedArrays {

    private MergeKSortedArrays() {
        // Prevent instantiation
    }

    /**
     * Merges k sorted arrays into one sorted array using a min-heap.
     *
     * @param arrays a 2D array, where each subarray is sorted in non-decreasing order
     * @return a single sorted array containing all elements from the input arrays
     */
    public static int[] mergeKArrays(int[][] arrays) {
        // Min-heap storing {value, arrayIndex, elementIndex}
        PriorityQueue<int[]> minHeap =
                new PriorityQueue<>(Comparator.comparingInt(entry -> entry[0]));

        int totalLength = 0;

        // Initialize heap with the first element of each non-empty array
        for (int arrayIndex = 0; arrayIndex < arrays.length; arrayIndex++) {
            if (arrays[arrayIndex].length > 0) {
                minHeap.offer(new int[] {arrays[arrayIndex][0], arrayIndex, 0});
                totalLength += arrays[arrayIndex].length;
            }
        }

        int[] result = new int[totalLength];
        int resultIndex = 0;

        // Extract the smallest element and push the next element from the same array
        while (!minHeap.isEmpty()) {
            int[] smallestEntry = minHeap.poll();
            int value = smallestEntry[0];
            int arrayIndex = smallestEntry[1];
            int elementIndex = smallestEntry[2];

            result[resultIndex++] = value;

            int nextElementIndex = elementIndex + 1;
            if (nextElementIndex < arrays[arrayIndex].length) {
                minHeap.offer(
                        new int[] {arrays[arrayIndex][nextElementIndex], arrayIndex, nextElementIndex});
            }
        }

        return result;
    }
}