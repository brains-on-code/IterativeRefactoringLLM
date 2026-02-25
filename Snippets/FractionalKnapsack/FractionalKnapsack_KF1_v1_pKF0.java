package com.thealgorithms.greedyalgorithms;

import java.util.Arrays;
import java.util.Comparator;

public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    /**
     * Computes the maximum value achievable with a given capacity using a
     * fractional knapsack-like greedy approach.
     *
     * @param weights  array of item weights
     * @param values   array of item values
     * @param capacity maximum total weight allowed
     * @return maximum total value achievable (integer approximation)
     */
    public static int method1(int[] weights, int[] values, int capacity) {
        int n = weights.length;
        double[][] valuePerWeightWithIndex = new double[n][2];

        for (int i = 0; i < n; i++) {
            valuePerWeightWithIndex[i][0] = i; // store original index
            valuePerWeightWithIndex[i][1] = values[i] / (double) weights[i]; // value/weight ratio
        }

        Arrays.sort(valuePerWeightWithIndex, Comparator.comparingDouble(o -> o[1]));

        int totalValue = 0;
        double remainingCapacity = capacity;

        for (int i = n - 1; i >= 0; i--) {
            int index = (int) valuePerWeightWithIndex[i][0];
            int weight = weights[index];
            int value = values[index];
            double ratio = valuePerWeightWithIndex[i][1];

            if (remainingCapacity >= weight) {
                totalValue += value;
                remainingCapacity -= weight;
            } else {
                totalValue += (int) (ratio * remainingCapacity);
                break;
            }
        }

        return totalValue;
    }
}