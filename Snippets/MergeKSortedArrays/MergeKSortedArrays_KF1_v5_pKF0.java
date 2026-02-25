package com.thealgorithms.datastructures.heaps;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Utility class for heap-based operations.
 */
public final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

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
     * Merges multiple sorted integer arrays into a single sorted array.
     *
     * @param arrays an array of sorted int arrays
     * @return a single sorted array containing all elements from the input arrays
     */
    public static int[] mergeSortedArrays(int[][] arrays) {
        if (arrays == null || arrays.length == 0) {
            return new int[0];
        }

        PriorityQueue<HeapEntry> minHeap =
            new PriorityQueue<>(Comparator.comparingInt(entry -> entry.value));

        int totalLength = 0;

        for (int arrayIndex = 0; arrayIndex < arrays.length; arrayIndex++) {
            int[] currentArray = arrays[arrayIndex];
            if (currentArray == null || currentArray.length == 0) {
                continue;
            }
            minHeap.offer(new HeapEntry(currentArray[0], arrayIndex, 0));
            totalLength += currentArray.length;
        }

        int[] merged = new int[totalLength];
        int mergedIndex = 0;

        while (!minHeap.isEmpty()) {
            HeapEntry smallestEntry = minHeap.poll();
            merged[mergedIndex++] = smallestEntry.value;

            int nextElementIndex = smallestEntry.elementIndex + 1;
            int[] sourceArray = arrays[smallestEntry.arrayIndex];

            if (nextElementIndex < sourceArray.length) {
                minHeap.offer(
                    new HeapEntry(
                        sourceArray[nextElementIndex],
                        smallestEntry.arrayIndex,
                        nextElementIndex
                    )
                );
            }
        }

        return merged;
    }
}