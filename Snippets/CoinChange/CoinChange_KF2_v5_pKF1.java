package com.thealgorithms.dynamicprogramming;

public final class CoinChange {

    private CoinChange() {
        // Utility class; prevent instantiation
    }

    /**
     * Counts the number of distinct ways to make up the given amount
     * using the provided coin denominations.
     */
    public static int countCombinations(int[] denominations, int targetAmount) {
        int[] combinationCounts = new int[targetAmount + 1];
        combinationCounts[0] = 1;

        for (int denomination : denominations) {
            for (int currentAmount = denomination; currentAmount <= targetAmount; currentAmount++) {
                combinationCounts[currentAmount] += combinationCounts[currentAmount - denomination];
            }
        }

        return combinationCounts[targetAmount];
    }

    /**
     * Computes the minimum number of coins needed to make up the given amount
     * using the provided coin denominations. Returns Integer.MAX_VALUE if the
     * amount cannot be formed.
     */
    public static int findMinimumCoins(int[] denominations, int targetAmount) {
        int[] minCoinsForAmount = new int[targetAmount + 1];
        minCoinsForAmount[0] = 0;

        for (int amount = 1; amount <= targetAmount; amount++) {
            minCoinsForAmount[amount] = Integer.MAX_VALUE;
        }

        for (int currentAmount = 1; currentAmount <= targetAmount; currentAmount++) {
            for (int denomination : denominations) {
                if (denomination <= currentAmount) {
                    int remainingAmount = currentAmount - denomination;
                    int minCoinsForRemaining = minCoinsForAmount[remainingAmount];

                    if (minCoinsForRemaining != Integer.MAX_VALUE
                            && minCoinsForRemaining + 1 < minCoinsForAmount[currentAmount]) {
                        minCoinsForAmount[currentAmount] = minCoinsForRemaining + 1;
                    }
                }
            }
        }

        return minCoinsForAmount[targetAmount];
    }
}