package com.thealgorithms.datastructures.heaps;

import java.util.Comparator;
import java.util.PriorityQueue;

public final class MergeKSortedArrays {

    private MergeKSortedArrays() {
        // Prevent instantiation
    }

    /**
     * Merges k sorted integer arrays into a single sorted array.
     *
     * @param arrays an array of sorted integer arrays
     * @return a single sorted array containing all elements from the input arrays
     */
    public static int[] mergeKArrays(int[][] arrays) {
        // Each heap entry: [value, arrayIndex, elementIndex]
        PriorityQueue<int[]> minHeap =
            new PriorityQueue<>(Comparator.comparingInt(entry -> entry[0]));

        int totalLength = 0;

        // Add the first element of each non-empty array to the heap
        for (int arrayIndex = 0; arrayIndex < arrays.length; arrayIndex++) {
            if (arrays[arrayIndex].length == 0) {
                continue;
            }
            minHeap.offer(new int[] {arrays[arrayIndex][0], arrayIndex, 0});
            totalLength += arrays[arrayIndex].length;
        }

        int[] result = new int[totalLength];
        int resultIndex = 0;

        // Repeatedly extract the smallest element and add the next element from the same array
        while (!minHeap.isEmpty()) {
            int[] entry = minHeap.poll();
            int value = entry[0];
            int arrayIndex = entry[1];
            int elementIndex = entry[2];

            result[resultIndex++] = value;

            int nextElementIndex = elementIndex + 1;
            if (nextElementIndex < arrays[arrayIndex].length) {
                minHeap.offer(
                    new int[] {arrays[arrayIndex][nextElementIndex], arrayIndex, nextElementIndex}
                );
            }
        }

        return result;
    }
}