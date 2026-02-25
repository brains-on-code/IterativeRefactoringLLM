package com.thealgorithms.datastructures.heaps;

import java.util.Comparator;
import java.util.PriorityQueue;

public final class MergeKSortedArrays {

    private MergeKSortedArrays() {
        // Utility class; prevent instantiation
    }

    public static int[] mergeKArrays(int[][] arrays) {
        if (arrays == null || arrays.length == 0) {
            return new int[0];
        }

        // Each element in the heap: {value, arrayIndex, elementIndex}
        PriorityQueue<int[]> minHeap =
                new PriorityQueue<>(Comparator.comparingInt(element -> element[0]));

        int totalLength = 0;

        for (int arrayIndex = 0; arrayIndex < arrays.length; arrayIndex++) {
            int[] currentArray = arrays[arrayIndex];
            if (currentArray != null && currentArray.length > 0) {
                minHeap.offer(new int[] {currentArray[0], arrayIndex, 0});
                totalLength += currentArray.length;
            }
        }

        int[] mergedResult = new int[totalLength];
        int resultIndex = 0;

        while (!minHeap.isEmpty()) {
            int[] smallestElement = minHeap.poll();
            int value = smallestElement[0];
            int arrayIndex = smallestElement[1];
            int elementIndex = smallestElement[2];

            mergedResult[resultIndex++] = value;

            int nextElementIndex = elementIndex + 1;
            int[] sourceArray = arrays[arrayIndex];

            if (nextElementIndex < sourceArray.length) {
                minHeap.offer(new int[] {sourceArray[nextElementIndex], arrayIndex, nextElementIndex});
            }
        }

        return mergedResult;
    }
}