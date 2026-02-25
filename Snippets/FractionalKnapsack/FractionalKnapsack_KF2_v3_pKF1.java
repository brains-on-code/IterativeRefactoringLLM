package com.thealgorithms.greedyalgorithms;

import java.util.Arrays;
import java.util.Comparator;

public final class FractionalKnapsack {

    private FractionalKnapsack() {
    }

    public static int fractionalKnapsack(int[] weights, int[] values, int capacity) {
        double[][] itemIndexAndValueDensity = new double[weights.length][2];

        for (int itemIndex = 0; itemIndex < weights.length; itemIndex++) {
            itemIndexAndValueDensity[itemIndex][0] = itemIndex;
            itemIndexAndValueDensity[itemIndex][1] = values[itemIndex] / (double) weights[itemIndex];
        }

        Arrays.sort(itemIndexAndValueDensity, Comparator.comparingDouble(item -> item[1]));

        int totalValue = 0;
        double remainingCapacity = capacity;

        for (int sortedIndex = itemIndexAndValueDensity.length - 1; sortedIndex >= 0; sortedIndex--) {
            int originalItemIndex = (int) itemIndexAndValueDensity[sortedIndex][0];
            double valueDensity = itemIndexAndValueDensity[sortedIndex][1];

            int itemWeight = weights[originalItemIndex];
            int itemValue = values[originalItemIndex];

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