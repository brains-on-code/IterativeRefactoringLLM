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

        for (int i = itemIndexAndValueDensity.length - 1; i >= 0; i--) {
            int itemIndex = (int) itemIndexAndValueDensity[i][0];
            double valueDensity = itemIndexAndValueDensity[i][1];

            if (remainingCapacity >= weights[itemIndex]) {
                totalValue += values[itemIndex];
                remainingCapacity -= weights[itemIndex];
            } else {
                totalValue += (int) (valueDensity * remainingCapacity);
                break;
            }
        }

        return totalValue;
    }
}