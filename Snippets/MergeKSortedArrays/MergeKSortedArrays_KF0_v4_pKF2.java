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
        // Utility class; prevent instantiation
    }

    /**
     * Represents an entry in the heap: the value, the index of the source array,
     * and the index of the element within that array.
     */
    private static final class HeapEntry {
        private final int value;
        private final int arrayIndex;
        private final int elementIndex;

        private HeapEntry(int value, int arrayIndex, int elementIndex) {
            this.value = value;
            this.arrayIndex = arrayIndex;
            this.elementIndex = elementIndex;
        }
    }

    /**
     * Merges k sorted arrays into one sorted array using a min-heap.
     *
     * @param arrays a 2D array, where each subarray is sorted in non-decreasing order
     * @return a single sorted array containing all elements from the input arrays
     */
    public static int[] mergeKArrays(int[][] arrays) {
        PriorityQueue<HeapEntry> minHeap =
                new PriorityQueue<>(Comparator.comparingInt(entry -> entry.value));

        int totalLength = 0;

        for (int arrayIndex = 0; arrayIndex < arrays.length; arrayIndex++) {
            int[] currentArray = arrays[arrayIndex];
            if (currentArray.length == 0) {
                continue;
            }
            minHeap.offer(new HeapEntry(currentArray[0], arrayIndex, 0));
            totalLength += currentArray.length;
        }

        int[] result = new int[totalLength];
        int resultIndex = 0;

        while (!minHeap.isEmpty()) {
            HeapEntry smallestEntry = minHeap.poll();
            result[resultIndex++] = smallestEntry.value;

            int nextElementIndex = smallestEntry.elementIndex + 1;
            int[] sourceArray = arrays[smallestEntry.arrayIndex];

            if (nextElementIndex < sourceArray.length) {
                minHeap.offer(
                        new HeapEntry(
                                sourceArray[nextElementIndex],
                                smallestEntry.arrayIndex,
                                nextElementIndex));
            }
        }

        return result;
    }
}