package com.thealgorithms.datastructures.heaps;

import java.util.Comparator;
import java.util.PriorityQueue;

public final class MergeKSortedArrays {

    private MergeKSortedArrays() {
    }

    public static int[] mergeKArrays(int[][] sortedArrays) {
        // Each heap element: [value, sourceArrayIndex, elementIndexInSource]
        PriorityQueue<int[]> minHeap =
                new PriorityQueue<>(Comparator.comparingInt(entry -> entry[0]));

        int mergedLength = 0;
        for (int sourceArrayIndex = 0; sourceArrayIndex < sortedArrays.length; sourceArrayIndex++) {
            int[] currentArray = sortedArrays[sourceArrayIndex];
            if (currentArray.length > 0) {
                minHeap.offer(new int[] {currentArray[0], sourceArrayIndex, 0});
                mergedLength += currentArray.length;
            }
        }

        int[] mergedArray = new int[mergedLength];
        int mergedArrayWriteIndex = 0;

        while (!minHeap.isEmpty()) {
            int[] heapEntry = minHeap.poll();
            int value = heapEntry[0];
            int sourceArrayIndex = heapEntry[1];
            int elementIndexInSource = heapEntry[2];

            mergedArray[mergedArrayWriteIndex++] = value;

            int nextElementIndexInSource = elementIndexInSource + 1;
            int[] sourceArray = sortedArrays[sourceArrayIndex];
            if (nextElementIndexInSource < sourceArray.length) {
                int nextValue = sourceArray[nextElementIndexInSource];
                minHeap.offer(new int[] {nextValue, sourceArrayIndex, nextElementIndexInSource});
            }
        }

        return mergedArray;
    }
}