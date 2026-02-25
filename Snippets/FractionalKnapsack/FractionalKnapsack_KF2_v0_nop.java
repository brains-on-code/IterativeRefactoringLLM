package com.thealgorithms.greedyalgorithms;

import java.util.Arrays;
import java.util.Comparator;


public final class FractionalKnapsack {
    private FractionalKnapsack() {
    }


    public static int fractionalKnapsack(int[] weight, int[] value, int capacity) {
        double[][] ratio = new double[weight.length][2];

        for (int i = 0; i < weight.length; i++) {
            ratio[i][0] = i;
            ratio[i][1] = value[i] / (double) weight[i];
        }

        Arrays.sort(ratio, Comparator.comparingDouble(o -> o[1]));

        int finalValue = 0;
        double current = capacity;

        for (int i = ratio.length - 1; i >= 0; i--) {
            int index = (int) ratio[i][0];
            if (current >= weight[index]) {
                finalValue += value[index];
                current -= weight[index];
            } else {
                finalValue += (int) (ratio[i][1] * current);
                break;
            }
        }
        return finalValue;
    }
}
