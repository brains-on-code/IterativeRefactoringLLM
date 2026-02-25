package com.thealgorithms.datastructures.heaps;

import java.util.Comparator;
import java.util.PriorityQueue;

public final class MergeKSortedArrays {

    private MergeKSortedArrays() {
    }

    public static int[] mergeKArrays(int[][] arrays) {
        // Each heap element: [value, arrayIndex, elementIndex]
        PriorityQueue<int[]> minHeap =
                new PriorityQueue<>(Comparator.comparingInt(element -> element[0]));

        int mergedArrayLength = 0;
        for (int arrayIndex = 0; arrayIndex < arrays.length; arrayIndex++) {
            if (arrays[arrayIndex].length > 0) {
                minHeap.offer(new int[] {arrays[arrayIndex][0], arrayIndex, 0});
                mergedArrayLength += arrays[arrayIndex].length;
            }
        }

        int[] mergedArray = new int[mergedArrayLength];
        int mergedIndex = 0;

        while (!minHeap.isEmpty()) {
            int[] currentElement = minHeap.poll();
            int value = currentElement[0];
            int arrayIndex = currentElement[1];
            int elementIndex = currentElement[2];

            mergedArray[mergedIndex++] = value;

            int nextElementIndex = elementIndex + 1;
            if (nextElementIndex < arrays[arrayIndex].length) {
                int nextValue = arrays[arrayIndex][nextElementIndex];
                minHeap.offer(new int[] {nextValue, arrayIndex, nextElementIndex});
            }
        }

        return mergedArray;
    }
}