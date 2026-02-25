package com.thealgorithms.greedyalgorithms;

import java.util.Arrays;
import java.util.Comparator;

public final class FractionalKnapsack {

    private FractionalKnapsack() {
    }

    public static int fractionalKnapsack(int[] weights, int[] values, int capacity) {
        double[][] itemIndexAndValuePerWeight = new double[weights.length][2];

        for (int index = 0; index < weights.length; index++) {
            itemIndexAndValuePerWeight[index][0] = index;
            itemIndexAndValuePerWeight[index][1] = values[index] / (double) weights[index];
        }

        Arrays.sort(itemIndexAndValuePerWeight, Comparator.comparingDouble(item -> item[1]));

        int totalValue = 0;
        double remainingCapacity = capacity;

        for (int i = itemIndexAndValuePerWeight.length - 1; i >= 0; i--) {
            int itemIndex = (int) itemIndexAndValuePerWeight[i][0];
            double valuePerWeight = itemIndexAndValuePerWeight[i][1];

            int itemWeight = weights[itemIndex];
            int itemValue = values[itemIndex];

            if (remainingCapacity >= itemWeight) {
                totalValue += itemValue;
                remainingCapacity -= itemWeight;
            } else {
                totalValue += (int) (valuePerWeight * remainingCapacity);
                break;
            }
        }

        return totalValue;
    }
}