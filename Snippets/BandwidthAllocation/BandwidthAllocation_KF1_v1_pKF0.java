package com.thealgorithms.greedyalgorithms;

import java.util.Arrays;

/**
 * Greedy algorithm to maximize value given a weight capacity, where items can be
 * taken fractionally (fractional knapsack).
 */
public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    /**
     * Computes the maximum total value that can be obtained with a given capacity,
     * where each item can be taken fully or fractionally.
     *
     * @param capacity the maximum total weight that can be taken
     * @param weights  the weights of the items
     * @param values   the values of the items
     * @return the maximum total value achievable
     */
    public static int method1(int capacity, int[] weights, int[] values) {
        int itemCount = weights.length;
        double[][] valuePerWeightWithIndex = new double[itemCount][2]; // {index, value/weight}

        for (int i = 0; i < itemCount; i++) {
            valuePerWeightWithIndex[i][0] = i;
            valuePerWeightWithIndex[i][1] = (double) values[i] / weights[i];
        }

        Arrays.sort(valuePerWeightWithIndex, (a, b) -> Double.compare(b[1], a[1]));

        int totalValue = 0;
        for (int i = 0; i < itemCount && capacity > 0; i++) {
            int index = (int) valuePerWeightWithIndex[i][0];
            int weight = weights[index];
            int value = values[index];
            double valuePerWeight = valuePerWeightWithIndex[i][1];

            if (capacity >= weight) {
                totalValue += value;
                capacity -= weight;
            } else {
                totalValue += (int) (valuePerWeight * capacity);
                break;
            }
        }

        return totalValue;
    }
}