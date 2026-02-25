package com.thealgorithms.greedyalgorithms;

import java.util.Arrays;
import java.util.Comparator;

public final class FractionalKnapsack {

    private FractionalKnapsack() {
    }

    public static int getMaxValue(int[] weights, int[] values, int capacity) {
        double[][] itemIndexAndValueDensity = new double[weights.length][2];

        for (int itemIndex = 0; itemIndex < weights.length; itemIndex++) {
            itemIndexAndValueDensity[itemIndex][0] = itemIndex;
            itemIndexAndValueDensity[itemIndex][1] = values[itemIndex] / (double) weights[itemIndex];
        }

        Arrays.sort(itemIndexAndValueDensity, Comparator.comparingDouble(item -> item[1]));

        int totalValue = 0;
        double remainingCapacity = capacity;

        for (int i = itemIndexAndValueDensity.length - 1; i >= 0; i--) {
            int originalIndex = (int) itemIndexAndValueDensity[i][0];
            double valueDensity = itemIndexAndValueDensity[i][1];

            if (remainingCapacity >= weights[originalIndex]) {
                totalValue += values[originalIndex];
                remainingCapacity -= weights[originalIndex];
            } else {
                totalValue += (int) (valueDensity * remainingCapacity);
                break;
            }
        }

        return totalValue;
    }
}