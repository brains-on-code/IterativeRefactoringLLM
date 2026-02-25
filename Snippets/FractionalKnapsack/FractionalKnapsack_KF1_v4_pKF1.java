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

        for (int sortedItemIndex = itemIndexAndValueDensity.length - 1; sortedItemIndex >= 0; sortedItemIndex--) {
            int originalItemIndex = (int) itemIndexAndValueDensity[sortedItemIndex][0];
            double valueDensity = itemIndexAndValueDensity[sortedItemIndex][1];

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