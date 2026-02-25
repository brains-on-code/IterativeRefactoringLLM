package com.thealgorithms.datastructures.heaps;

import java.util.Comparator;
import java.util.PriorityQueue;

public final class MergeKSortedArrays {

    private MergeKSortedArrays() {
    }

    private static final int HEAP_VALUE_INDEX = 0;
    private static final int HEAP_SOURCE_ARRAY_INDEX = 1;
    private static final int HEAP_ELEMENT_INDEX = 2;

    public static int[] mergeKArrays(int[][] sortedArrays) {
        // Each heap element: [value, sourceArrayIndex, elementIndex]
        PriorityQueue<int[]> minHeap =
                new PriorityQueue<>(Comparator.comparingInt(entry -> entry[HEAP_VALUE_INDEX]));

        int totalElementsCount = 0;
        for (int sourceArrayIndex = 0; sourceArrayIndex < sortedArrays.length; sourceArrayIndex++) {
            int[] sourceArray = sortedArrays[sourceArrayIndex];
            if (sourceArray.length > 0) {
                minHeap.offer(new int[] {sourceArray[0], sourceArrayIndex, 0});
                totalElementsCount += sourceArray.length;
            }
        }

        int[] mergedArray = new int[totalElementsCount];
        int mergedArrayWriteIndex = 0;

        while (!minHeap.isEmpty()) {
            int[] heapEntry = minHeap.poll();
            int value = heapEntry[HEAP_VALUE_INDEX];
            int sourceArrayIndex = heapEntry[HEAP_SOURCE_ARRAY_INDEX];
            int elementIndexInSource = heapEntry[HEAP_ELEMENT_INDEX];

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