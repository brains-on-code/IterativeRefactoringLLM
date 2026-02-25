package com.thealgorithms.datastructures.heaps;

import java.util.Comparator;
import java.util.PriorityQueue;


public final class MergeKSortedArrays {
    private MergeKSortedArrays() {
    }


    public static int[] mergeKArrays(int[][] arrays) {
        PriorityQueue<int[]> minHeap = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));

        int totalLength = 0;
        for (int i = 0; i < arrays.length; i++) {
            if (arrays[i].length > 0) {
                minHeap.offer(new int[] {arrays[i][0], i, 0});
                totalLength += arrays[i].length;
            }
        }

        int[] result = new int[totalLength];
        int index = 0;
        while (!minHeap.isEmpty()) {
            int[] top = minHeap.poll();
            result[index++] = top[0];

            if (top[2] + 1 < arrays[top[1]].length) {
                minHeap.offer(new int[] {arrays[top[1]][top[2] + 1], top[1], top[2] + 1});
            }
        }

        return result;
    }
}
