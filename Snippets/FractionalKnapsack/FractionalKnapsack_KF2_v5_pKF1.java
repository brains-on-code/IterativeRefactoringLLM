package com.thealgorithms.greedyalgorithms;

import java.util.Arrays;
import java.util.Comparator;

public final class FractionalKnapsack {

    private FractionalKnapsack() {
    }

    public static int fractionalKnapsack(int[] weights, int[] values, int capacity) {
        double[][] itemIndexAndValueDensity = new double[weights.length][2];

        for (int i = 0; i < weights.length; i++) {
            itemIndexAndValueDensity[i][0] = i;
            itemIndexAndValueDensity[i][1] = values[i] / (double) weights[i];
        }

        Arrays.sort(itemIndexAndValueDensity, Comparator.comparingDouble(item -> item[1]));

        int totalValue = 0;
        double remainingCapacity = capacity;

        for (int i = itemIndexAndValueDensity.length - 1; i >= 0; i--) {
            int itemIndex = (int) itemIndexAndValueDensity[i][0];
            double valueDensity = itemIndexAndValueDensity[i][1];

            int itemWeight = weights[itemIndex];
            int itemValue = values[itemIndex];

            if (remainingCapacity >= itemWeight) {
                totalValue += itemValue;
                remainingCapacity -= itemWeight;
            } else {
                totalValue += (int) (valueDensity * remainingCapacity);
                break;
            }
        }

        return totalValue;
    }
}