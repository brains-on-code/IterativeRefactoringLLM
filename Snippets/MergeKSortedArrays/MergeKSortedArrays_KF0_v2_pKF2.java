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

    private static final int VALUE_INDEX = 0;
    private static final int ARRAY_INDEX = 1;
    private static final int ELEMENT_INDEX = 2;

    private MergeKSortedArrays() {
        // Utility class; prevent instantiation
    }

    /**
     * Merges k sorted arrays into one sorted array using a min-heap.
     *
     * @param arrays a 2D array, where each subarray is sorted in non-decreasing order
     * @return a single sorted array containing all elements from the input arrays
     */
    public static int[] mergeKArrays(int[][] arrays) {
        PriorityQueue<int[]> minHeap =
                new PriorityQueue<>(Comparator.comparingInt(entry -> entry[VALUE_INDEX]));

        int totalLength = 0;

        for (int arrayIndex = 0; arrayIndex < arrays.length; arrayIndex++) {
            int[] currentArray = arrays[arrayIndex];
            if (currentArray.length == 0) {
                continue;
            }
            minHeap.offer(new int[] {currentArray[0], arrayIndex, 0});
            totalLength += currentArray.length;
        }

        int[] result = new int[totalLength];
        int resultIndex = 0;

        while (!minHeap.isEmpty()) {
            int[] smallestEntry = minHeap.poll();
            int value = smallestEntry[VALUE_INDEX];
            int arrayIndex = smallestEntry[ARRAY_INDEX];
            int elementIndex = smallestEntry[ELEMENT_INDEX];

            result[resultIndex++] = value;

            int nextElementIndex = elementIndex + 1;
            int[] sourceArray = arrays[arrayIndex];
            if (nextElementIndex < sourceArray.length) {
                minHeap.offer(
                        new int[] {sourceArray[nextElementIndex], arrayIndex, nextElementIndex});
            }
        }

        return result;
    }
}