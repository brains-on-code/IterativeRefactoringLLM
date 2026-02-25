package com.thealgorithms.greedyalgorithms;

import java.util.Arrays;
import java.util.Comparator;

/**
 * The FractionalKnapsack class provides a method to solve the fractional knapsack problem
 * using a greedy algorithm approach. It allows for selecting fractions of items to maximize
 * the total value in a knapsack with a given weight capacity.
 *
 * The problem consists of a set of items, each with a weight and a value, and a knapsack
 * that can carry a maximum weight. The goal is to maximize the value of items in the knapsack,
 * allowing for the inclusion of fractions of items.
 *
 * Problem Link: https://en.wikipedia.org/wiki/Continuous_knapsack_problem
 */
public final class FractionalKnapsack {

    private FractionalKnapsack() {
    }

    /**
     * Computes the maximum value that can be accommodated in a knapsack of a given capacity.
     *
     * @param weights  an array of integers representing the weights of the items
     * @param values   an array of integers representing the values of the items
     * @param capacity an integer representing the maximum weight capacity of the knapsack
     * @return the maximum value that can be obtained by including the items in the knapsack
     */
    public static int fractionalKnapsack(int[] weights, int[] values, int capacity) {
        double[][] itemIndexAndValueDensity = new double[weights.length][2];

        for (int itemIndex = 0; itemIndex < weights.length; itemIndex++) {
            itemIndexAndValueDensity[itemIndex][0] = itemIndex;
            itemIndexAndValueDensity[itemIndex][1] = values[itemIndex] / (double) weights[itemIndex];
        }

        Arrays.sort(itemIndexAndValueDensity, Comparator.comparingDouble(item -> item[1]));

        int totalValue = 0;
        double remainingCapacity = capacity;

        for (int itemPosition = itemIndexAndValueDensity.length - 1; itemPosition >= 0; itemPosition--) {
            int originalItemIndex = (int) itemIndexAndValueDensity[itemPosition][0];
            double valueDensity = itemIndexAndValueDensity[itemPosition][1];

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