package com.thealgorithms.dynamicprogramming;

public final class CoinChange {

    private CoinChange() {
        // Utility class; prevent instantiation
    }

    /**
     * Counts the number of distinct ways to make up the given amount
     * using the provided coin denominations.
     */
    public static int countCombinations(int[] coinDenominations, int targetAmount) {
        int[] combinationCounts = new int[targetAmount + 1];
        combinationCounts[0] = 1;

        for (int coinValue : coinDenominations) {
            for (int currentAmount = coinValue; currentAmount <= targetAmount; currentAmount++) {
                combinationCounts[currentAmount] += combinationCounts[currentAmount - coinValue];
            }
        }

        return combinationCounts[targetAmount];
    }

    /**
     * Computes the minimum number of coins needed to make up the given amount
     * using the provided coin denominations. Returns Integer.MAX_VALUE if the
     * amount cannot be formed.
     */
    public static int findMinimumCoins(int[] coinDenominations, int targetAmount) {
        int[] minCoinsForAmount = new int[targetAmount + 1];
        minCoinsForAmount[0] = 0;

        for (int amount = 1; amount <= targetAmount; amount++) {
            minCoinsForAmount[amount] = Integer.MAX_VALUE;
        }

        for (int amount = 1; amount <= targetAmount; amount++) {
            for (int coinValue : coinDenominations) {
                if (coinValue <= amount) {
                    int remainingAmount = amount - coinValue;
                    int coinsNeededForRemaining = minCoinsForAmount[remainingAmount];

                    if (coinsNeededForRemaining != Integer.MAX_VALUE
                            && coinsNeededForRemaining + 1 < minCoinsForAmount[amount]) {
                        minCoinsForAmount[amount] = coinsNeededForRemaining + 1;
                    }
                }
            }
        }

        return minCoinsForAmount[targetAmount];
    }
}