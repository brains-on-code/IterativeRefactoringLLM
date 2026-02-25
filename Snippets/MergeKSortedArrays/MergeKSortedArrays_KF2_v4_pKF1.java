package com.thealgorithms.datastructures.heaps;

import java.util.Comparator;
import java.util.PriorityQueue;

public final class MergeKSortedArrays {

    private MergeKSortedArrays() {
    }

    private static final int VALUE_INDEX = 0;
    private static final int ARRAY_INDEX = 1;
    private static final int ELEMENT_INDEX = 2;

    public static int[] mergeKArrays(int[][] sortedArrays) {
        // Each heap element: [value, arrayIndex, elementIndex]
        PriorityQueue<int[]> minHeap =
                new PriorityQueue<>(Comparator.comparingInt(entry -> entry[VALUE_INDEX]));

        int totalMergedLength = 0;
        for (int arrayIndex = 0; arrayIndex < sortedArrays.length; arrayIndex++) {
            int[] currentArray = sortedArrays[arrayIndex];
            if (currentArray.length > 0) {
                minHeap.offer(new int[] {currentArray[0], arrayIndex, 0});
                totalMergedLength += currentArray.length;
            }
        }

        int[] mergedArray = new int[totalMergedLength];
        int mergedArrayPosition = 0;

        while (!minHeap.isEmpty()) {
            int[] heapEntry = minHeap.poll();
            int value = heapEntry[VALUE_INDEX];
            int arrayIndex = heapEntry[ARRAY_INDEX];
            int elementIndex = heapEntry[ELEMENT_INDEX];

            mergedArray[mergedArrayPosition++] = value;

            int nextElementIndex = elementIndex + 1;
            int[] sourceArray = sortedArrays[arrayIndex];
            if (nextElementIndex < sourceArray.length) {
                int nextValue = sourceArray[nextElementIndex];
                minHeap.offer(new int[] {nextValue, arrayIndex, nextElementIndex});
            }
        }

        return mergedArray;
    }
}