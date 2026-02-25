package com.thealgorithms.datastructures.heaps;

import java.util.Comparator;
import java.util.PriorityQueue;

public final class MergeKSortedArrays {

    private MergeKSortedArrays() {
    }

    public static int[] mergeKArrays(int[][] sortedArrays) {
        // Each heap element: [value, arrayIndex, elementIndex]
        PriorityQueue<int[]> minHeap =
                new PriorityQueue<>(Comparator.comparingInt(entry -> entry[0]));

        int totalLength = 0;
        for (int arrayIndex = 0; arrayIndex < sortedArrays.length; arrayIndex++) {
            if (sortedArrays[arrayIndex].length > 0) {
                minHeap.offer(new int[] {sortedArrays[arrayIndex][0], arrayIndex, 0});
                totalLength += sortedArrays[arrayIndex].length;
            }
        }

        int[] mergedArray = new int[totalLength];
        int mergedArrayIndex = 0;

        while (!minHeap.isEmpty()) {
            int[] heapEntry = minHeap.poll();
            int value = heapEntry[0];
            int arrayIndex = heapEntry[1];
            int elementIndex = heapEntry[2];

            mergedArray[mergedArrayIndex++] = value;

            int nextElementIndex = elementIndex + 1;
            if (nextElementIndex < sortedArrays[arrayIndex].length) {
                int nextValue = sortedArrays[arrayIndex][nextElementIndex];
                minHeap.offer(new int[] {nextValue, arrayIndex, nextElementIndex});
            }
        }

        return mergedArray;
    }
}