package com.thealgorithms.datastructures.heaps;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * This class provides a method to merge multiple sorted arrays into a single sorted array.
 * It utilizes a min-heap to efficiently retrieve the smallest elements from each array.
 *
 * Time Complexity: O(n * log k), where n is the total number of elements across all arrays
 * and k is the number of arrays.
 *
 * Space Complexity: O(k) for the heap, where k is the number of arrays.
 *
 * @author Hardvan
 */
public final class MergeKSortedArrays {

    private MergeKSortedArrays() {
        // Utility class; prevent instantiation
    }

    /**
     * Helper record to store heap entries.
     * value: element value
     * arrayIndex: which array this value came from
     * elementIndex: index within that array
     */
    private record HeapEntry(int value, int arrayIndex, int elementIndex) {}

    /**
     * Merges k sorted arrays into one sorted array using a min-heap.
     *
     * @param arrays a 2D array, where each subarray is sorted in non-decreasing order
     * @return a single sorted array containing all elements from the input arrays
     */
    public static int[] mergeKArrays(int[][] arrays) {
        if (arrays == null || arrays.length == 0) {
            return new int[0];
        }

        PriorityQueue<HeapEntry> minHeap =
            new PriorityQueue<>(Comparator.comparingInt(HeapEntry::value));

        int totalLength = 0;
        for (int arrayIndex = 0; arrayIndex < arrays.length; arrayIndex++) {
            int[] currentArray = arrays[arrayIndex];
            if (currentArray == null || currentArray.length == 0) {
                continue;
            }
            minHeap.offer(new HeapEntry(currentArray[0], arrayIndex, 0));
            totalLength += currentArray.length;
        }

        int[] result = new int[totalLength];
        int resultIndex = 0;

        while (!minHeap.isEmpty()) {
            HeapEntry smallestEntry = minHeap.poll();
            result[resultIndex++] = smallestEntry.value();

            int nextElementIndex = smallestEntry.elementIndex() + 1;
            int[] sourceArray = arrays[smallestEntry.arrayIndex()];

            if (nextElementIndex < sourceArray.length) {
                minHeap.offer(
                    new HeapEntry(
                        sourceArray[nextElementIndex],
                        smallestEntry.arrayIndex(),
                        nextElementIndex
                    )
                );
            }
        }

        return result;
    }
}