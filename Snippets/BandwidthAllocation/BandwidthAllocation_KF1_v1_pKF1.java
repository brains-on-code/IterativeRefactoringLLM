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
        double[][] valuePerWeight = new double[itemCount][2]; // {index, value/weight}

        for (int i = 0; i < itemCount; i++) {
            valuePerWeight[i][0] = i;
            valuePerWeight[i][1] = (double) values[i] / weights[i];
        }

        Arrays.sort(valuePerWeight, (a, b) -> Double.compare(b[1], a[1]));

        int totalValue = 0;
        for (int i = 0; i < itemCount; i++) {
            int index = (int) valuePerWeight[i][0];
            if (capacity >= weights[index]) {
                totalValue += values[index];
                capacity -= weights[index];
            } else {
                totalValue += (int) (valuePerWeight[i][1] * capacity);
                break;
            }
        }
        return totalValue;
    }
}