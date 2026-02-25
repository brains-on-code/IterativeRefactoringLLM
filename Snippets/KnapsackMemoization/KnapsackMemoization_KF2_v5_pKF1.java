package com.thealgorithms.dynamicprogramming;

import java.util.Arrays;

public class KnapsackMemoization {

    int knapSack(int capacity, int[] weights, int[] values, int itemCount) {
        int[][] maxValueCache = new int[itemCount + 1][capacity + 1];

        for (int[] cacheRow : maxValueCache) {
            Arrays.fill(cacheRow, -1);
        }

        return computeMaxValue(capacity, weights, values, itemCount, maxValueCache);
    }

    int computeMaxValue(
            int remainingCapacity,
            int[] weights,
            int[] values,
            int itemIndex,
            int[][] maxValueCache) {

        if (itemIndex == 0 || remainingCapacity == 0) {
            return 0;
        }

        if (maxValueCache[itemIndex][remainingCapacity] != -1) {
            return maxValueCache[itemIndex][remainingCapacity];
        }

        int itemWeight = weights[itemIndex - 1];
        int itemValue = values[itemIndex - 1];

        if (itemWeight > remainingCapacity) {
            maxValueCache[itemIndex][remainingCapacity] =
                computeMaxValue(remainingCapacity, weights, values, itemIndex - 1, maxValueCache);
        } else {
            int valueWithItem =
                itemValue
                    + computeMaxValue(
                        remainingCapacity - itemWeight,
                        weights,
                        values,
                        itemIndex - 1,
                        maxValueCache);

            int valueWithoutItem =
                computeMaxValue(
                    remainingCapacity,
                    weights,
                    values,
                    itemIndex - 1,
                    maxValueCache);

            maxValueCache[itemIndex][remainingCapacity] = Math.max(valueWithItem, valueWithoutItem);
        }

        return maxValueCache[itemIndex][remainingCapacity];
    }
}