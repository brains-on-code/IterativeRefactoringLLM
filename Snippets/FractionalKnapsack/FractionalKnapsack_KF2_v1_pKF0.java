package com.thealgorithms.greedyalgorithms;

import java.util.Arrays;
import java.util.Comparator;

public final class FractionalKnapsack {

    private FractionalKnapsack() {
        // Utility class; prevent instantiation
    }

    public static int fractionalKnapsack(int[] weights, int[] values, int capacity) {
        int n = weights.length;
        double[][] valueToWeightRatios = new double[n][2];

        for (int i = 0; i < n; i++) {
            valueToWeightRatios[i][0] = i;
            valueToWeightRatios[i][1] = values[i] / (double) weights[i];
        }

        Arrays.sort(valueToWeightRatios, Comparator.comparingDouble(ratio -> ratio[1]));

        int totalValue = 0;
        double remainingCapacity = capacity;

        for (int i = n - 1; i >= 0 && remainingCapacity > 0; i--) {
            int itemIndex = (int) valueToWeightRatios[i][0];
            int itemWeight = weights[itemIndex];
            int itemValue = values[itemIndex];
            double ratio = valueToWeightRatios[i][1];

            if (remainingCapacity >= itemWeight) {
                totalValue += itemValue;
                remainingCapacity -= itemWeight;
            } else {
                totalValue += (int) (ratio * remainingCapacity);
                break;
            }
        }

        return totalValue;
    }
}