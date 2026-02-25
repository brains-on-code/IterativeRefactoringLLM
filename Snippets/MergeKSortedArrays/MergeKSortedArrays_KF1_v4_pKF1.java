package com.thealgorithms.datastructures.heaps;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Utility class for heap-based operations.
 */
public final class HeapUtils {

    private HeapUtils() {
        // Utility class; prevent instantiation
    }

    /**
     * Merges multiple sorted integer arrays into a single sorted array.
     *
     * @param sortedArrays an array of sorted integer arrays
     * @return a single merged sorted array containing all elements
     */
    public static int[] mergeSortedArrays(int[][] sortedArrays) {
        // Each heap element: {value, sourceArrayIndex, elementIndexInSource}
        PriorityQueue<int[]> minHeap =
                new PriorityQueue<>(Comparator.comparingInt(heapEntry -> heapEntry[0]));

        int totalElementCount = 0;

        for (int sourceArrayIndex = 0; sourceArrayIndex < sortedArrays.length; sourceArrayIndex++) {
            int[] sourceArray = sortedArrays[sourceArrayIndex];
            if (sourceArray.length > 0) {
                minHeap.offer(new int[] {sourceArray[0], sourceArrayIndex, 0});
                totalElementCount += sourceArray.length;
            }
        }

        int[] mergedArray = new int[totalElementCount];
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
                minHeap.offer(
                        new int[] {
                            sourceArray[nextElementIndexInSource],
                            sourceArrayIndex,
                            nextElementIndexInSource
                        });
            }
        }

        return mergedArray;
    }
}