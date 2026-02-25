package com.thealgorithms.dynamicprogramming;

/**
 * Utility class for coin change problems using dynamic programming.
 */
public final class CoinChange {

    private CoinChange() {
        // Prevent instantiation
    }

    /**
     * Calculates the number of combinations to make up a given amount using the provided coin denominations.
     * Order of coins does not matter (i.e., [1,2] is the same as [2,1]).
     *
     * @param coins  the available coin denominations
     * @param amount the target amount
     * @return the number of distinct combinations to form the amount
     */
    public static int change(int[] coins, int amount) {
        if (amount < 0 || coins == null || coins.length == 0) {
            return 0;
        }

        int[] combinations = new int[amount + 1];
        combinations[0] = 1; // Base case: one way to make amount 0 (use no coins)

        for (int coin : coins) {
            for (int currentAmount = coin; currentAmount <= amount; currentAmount++) {
                combinations[currentAmount] += combinations[currentAmount - coin];
            }
        }

        return combinations[amount];
    }

    /**
     * Calculates the minimum number of coins required to make up a given amount.
     *
     * @param coins  the available coin denominations
     * @param amount the target amount
     * @return the minimum number of coins needed to form the amount, or Integer.MAX_VALUE if not possible
     */
    public static int minimumCoins(int[] coins, int amount) {
        if (amount < 0 || coins == null || coins.length == 0) {
            return Integer.MAX_VALUE;
        }

        int[] minCoins = new int[amount + 1];
        minCoins[0] = 0;

        for (int currentAmount = 1; currentAmount <= amount; currentAmount++) {
            minCoins[currentAmount] = Integer.MAX_VALUE;
        }

        for (int currentAmount = 1; currentAmount <= amount; currentAmount++) {
            for (int coin : coins) {
                if (coin <= currentAmount && minCoins[currentAmount - coin] != Integer.MAX_VALUE) {
                    int candidateMin = minCoins[currentAmount - coin] + 1;
                    if (candidateMin < minCoins[currentAmount]) {
                        minCoins[currentAmount] = candidateMin;
                    }
                }
            }
        }

        return minCoins[amount];
    }
}