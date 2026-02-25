package com.thealgorithms.greedyalgorithms;

import java.util.Arrays;
import java.util.Comparator;

public final class FractionalKnapsack {

    private FractionalKnapsack() {
    }

    public static int fractionalKnapsack(int[] weights, int[] values, int capacity) {
        double[][] itemIndexAndValueDensity = new double[weights.length][2];

        for (int index = 0; index < weights.length; index++) {
            itemIndexAndValueDensity[index][0] = index;
            itemIndexAndValueDensity[index][1] = values[index] / (double) weights[index];
        }

        Arrays.sort(itemIndexAndValueDensity, Comparator.comparingDouble(item -> item[1]));

        int totalValue = 0;
        double remainingCapacity = capacity;

        for (int index = itemIndexAndValueDensity.length - 1; index >= 0; index--) {
            int itemIndex = (int) itemIndexAndValueDensity[index][0];
            double valueDensity = itemIndexAndValueDensity[index][1];

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