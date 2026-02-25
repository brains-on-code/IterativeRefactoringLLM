package com.thealgorithms.datastructures.heaps;

import java.util.Comparator;
import java.util.PriorityQueue;

public final class MergeKSortedArrays {

    private MergeKSortedArrays() {
        // Utility class; prevent instantiation
    }

    private static final class HeapNode {
        final int value;
        final int arrayIndex;
        final int elementIndex;

        HeapNode(int value, int arrayIndex, int elementIndex) {
            this.value = value;
            this.arrayIndex = arrayIndex;
            this.elementIndex = elementIndex;
        }
    }

    public static int[] mergeKArrays(int[][] arrays) {
        if (arrays == null || arrays.length == 0) {
            return new int[0];
        }

        PriorityQueue<HeapNode> minHeap =
                new PriorityQueue<>(Comparator.comparingInt(node -> node.value));

        int totalLength = 0;

        for (int arrayIndex = 0; arrayIndex < arrays.length; arrayIndex++) {
            int[] currentArray = arrays[arrayIndex];
            if (currentArray == null || currentArray.length == 0) {
                continue;
            }
            minHeap.offer(new HeapNode(currentArray[0], arrayIndex, 0));
            totalLength += currentArray.length;
        }

        int[] mergedResult = new int[totalLength];
        int resultIndex = 0;

        while (!minHeap.isEmpty()) {
            HeapNode smallestNode = minHeap.poll();
            mergedResult[resultIndex++] = smallestNode.value;

            int nextElementIndex = smallestNode.elementIndex + 1;
            int[] sourceArray = arrays[smallestNode.arrayIndex];

            if (nextElementIndex < sourceArray.length) {
                minHeap.offer(
                        new HeapNode(sourceArray[nextElementIndex], smallestNode.arrayIndex, nextElementIndex));
            }
        }

        return mergedResult;
    }
}