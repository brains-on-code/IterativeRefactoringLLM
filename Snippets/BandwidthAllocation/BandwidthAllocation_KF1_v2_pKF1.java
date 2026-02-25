package com.thealgorithms.greedyalgorithms;

import java.util.Arrays;

/**
 * Fractional Knapsack problem implementation.
 */
public final class FractionalKnapsack {

    private FractionalKnapsack() {
    }

    /**
     * Computes the maximum total value that can be put into a knapsack of given capacity,
     * where items can be broken into smaller parts (fractional knapsack).
     *
     * @param capacity the maximum weight capacity of the knapsack
     * @param weights  the weights of the items
     * @param values   the values of the items
     * @return the maximum total value achievable
     */
    public static int getMaxValue(int capacity, int[] weights, int[] values) {
        int itemCount = weights.length;
        double[][] itemValueDensity = new double[itemCount][2]; // {itemIndex, value/weight}

        for (int itemIndex = 0; itemIndex < itemCount; itemIndex++) {
            itemValueDensity[itemIndex][0] = itemIndex;
            itemValueDensity[itemIndex][1] = (double) values[itemIndex] / weights[itemIndex];
        }

        Arrays.sort(itemValueDensity, (firstItem, secondItem) -> Double.compare(secondItem[1], firstItem[1]));

        int totalValue = 0;
        int remainingCapacity = capacity;

        for (int i = 0; i < itemCount && remainingCapacity > 0; i++) {
            int itemIndex = (int) itemValueDensity[i][0];
            int itemWeight = weights[itemIndex];
            int itemValue = values[itemIndex];
            double valuePerWeight = itemValueDensity[i][1];

            if (remainingCapacity >= itemWeight) {
                totalValue += itemValue;
                remainingCapacity -= itemWeight;
            } else {
                totalValue += (int) (valuePerWeight * remainingCapacity);
                break;
            }
        }

        return totalValue;
    }
}