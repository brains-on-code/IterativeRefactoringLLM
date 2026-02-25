package com.thealgorithms.greedyalgorithms;

import java.util.Arrays;
import java.util.Comparator;

public final class FractionalKnapsack {

    private FractionalKnapsack() {
    }

    public static int getMaxValue(int[] weights, int[] values, int capacity) {
        double[][] valuePerWeight = new double[weights.length][2];

        for (int i = 0; i < weights.length; i++) {
            valuePerWeight[i][0] = i;
            valuePerWeight[i][1] = values[i] / (double) weights[i];
        }

        Arrays.sort(valuePerWeight, Comparator.comparingDouble(o -> o[1]));

        int totalValue = 0;
        double remainingCapacity = capacity;

        for (int i = valuePerWeight.length - 1; i >= 0; i--) {
            int index = (int) valuePerWeight[i][0];
            if (remainingCapacity >= weights[index]) {
                totalValue += values[index];
                remainingCapacity -= weights[index];
            } else {
                totalValue += (int) (valuePerWeight[i][1] * remainingCapacity);
                break;
            }
        }
        return totalValue;
    }
}