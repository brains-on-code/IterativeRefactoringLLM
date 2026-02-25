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
        int[] waysToMakeAmount = new int[targetAmount + 1];
        waysToMakeAmount[0] = 1;

        for (int coinValue : coinDenominations) {
            for (int amount = coinValue; amount <= targetAmount; amount++) {
                waysToMakeAmount[amount] += waysToMakeAmount[amount - coinValue];
            }
        }

        return waysToMakeAmount[targetAmount];
    }

    /**
     * Computes the minimum number of coins needed to make up the given amount
     * using the provided coin denominations. Returns Integer.MAX_VALUE if the
     * amount cannot be formed.
     */
    public static int findMinimumCoins(int[] coinDenominations, int targetAmount) {
        int[] minCoinsRequired = new int[targetAmount + 1];
        minCoinsRequired[0] = 0;

        for (int amount = 1; amount <= targetAmount; amount++) {
            minCoinsRequired[amount] = Integer.MAX_VALUE;
        }

        for (int amount = 1; amount <= targetAmount; amount++) {
            for (int coinValue : coinDenominations) {
                if (coinValue <= amount) {
                    int remainingAmount = amount - coinValue;
                    int coinsForRemaining = minCoinsRequired[remainingAmount];

                    if (coinsForRemaining != Integer.MAX_VALUE
                            && coinsForRemaining + 1 < minCoinsRequired[amount]) {
                        minCoinsRequired[amount] = coinsForRemaining + 1;
                    }
                }
            }
        }

        return minCoinsRequired[targetAmount];
    }
}