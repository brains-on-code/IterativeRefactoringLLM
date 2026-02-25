package com.thealgorithms.greedyalgorithms;

import java.util.Arrays;
import java.util.Comparator;

public final class FractionalKnapsack {

    private FractionalKnapsack() {
    }

    public static int getMaxValue(int[] weights, int[] values, int capacity) {
        double[][] itemIndexAndValueDensity = new double[weights.length][2];

        for (int index = 0; index < weights.length; index++) {
            itemIndexAndValueDensity[index][0] = index;
            itemIndexAndValueDensity[index][1] = values[index] / (double) weights[index];
        }

        Arrays.sort(itemIndexAndValueDensity, Comparator.comparingDouble(item -> item[1]));

        int totalValue = 0;
        double remainingCapacity = capacity;

        for (int itemIndex = itemIndexAndValueDensity.length - 1; itemIndex >= 0; itemIndex--) {
            int originalItemIndex = (int) itemIndexAndValueDensity[itemIndex][0];
            double valueDensity = itemIndexAndValueDensity[itemIndex][1];

            if (remainingCapacity >= weights[originalItemIndex]) {
                totalValue += values[originalItemIndex];
                remainingCapacity -= weights[originalItemIndex];
            } else {
                totalValue += (int) (valueDensity * remainingCapacity);
                break;
            }
        }

        return totalValue;
    }
}