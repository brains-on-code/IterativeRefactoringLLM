package com.thealgorithms.greedyalgorithms;

import java.util.Arrays;
import java.util.Comparator;

public final class FractionalKnapsack {

    private FractionalKnapsack() {
    }

    public static int fractionalKnapsack(int[] weights, int[] values, int capacity) {
        double[][] valuePerWeightRatios = new double[weights.length][2];

        for (int itemIndex = 0; itemIndex < weights.length; itemIndex++) {
            valuePerWeightRatios[itemIndex][0] = itemIndex;
            valuePerWeightRatios[itemIndex][1] = values[itemIndex] / (double) weights[itemIndex];
        }

        Arrays.sort(valuePerWeightRatios, Comparator.comparingDouble(ratio -> ratio[1]));

        int totalValue = 0;
        double remainingCapacity = capacity;

        for (int i = valuePerWeightRatios.length - 1; i >= 0; i--) {
            int itemIndex = (int) valuePerWeightRatios[i][0];
            double itemRatio = valuePerWeightRatios[i][1];

            if (remainingCapacity >= weights[itemIndex]) {
                totalValue += values[itemIndex];
                remainingCapacity -= weights[itemIndex];
            } else {
                totalValue += (int) (itemRatio * remainingCapacity);
                break;
            }
        }

        return totalValue;
    }
}