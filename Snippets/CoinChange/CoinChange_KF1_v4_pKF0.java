package com.thealgorithms.dynamicprogramming;

/**
 * Utility class for coin change dynamic programming algorithms.
 */
public final class CoinChangeUtils {

    private CoinChangeUtils() {
        // Prevent instantiation
    }

    /**
     * Counts the number of ways to make up a given amount using the provided coin
     * denominations. Order of coins does not matter.
     *
     * @param coins  array of coin denominations
     * @param amount target amount
     * @return number of combinations to form the target amount
     */
    public static int countCombinations(int[] coins, int amount) {
        if (amount < 0 || coins == null || coins.length == 0) {
            return 0;
        }

        int[] combinations = new int[amount + 1];
        combinations[0] = 1;

        for (int coin : coins) {
            for (int currentAmount = coin; currentAmount <= amount; currentAmount++) {
                combinations[currentAmount] += combinations[currentAmount - coin];
            }
        }

        return combinations[amount];
    }

    /**
     * Computes the minimum number of coins needed to make up a given amount using
     * the provided coin denominations.
     *
     * @param coins  array of coin denominations
     * @param amount target amount
     * @return minimum number of coins to form the target amount, or
     *         Integer.MAX_VALUE if it is not possible
     */
    public static int findMinimumCoins(int[] coins, int amount) {
        if (amount < 0 || coins == null || coins.length == 0) {
            return Integer.MAX_VALUE;
        }

        final int unreachable = Integer.MAX_VALUE;
        int[] minCoins = new int[amount + 1];

        minCoins[0] = 0;
        for (int currentAmount = 1; currentAmount <= amount; currentAmount++) {
            minCoins[currentAmount] = unreachable;
        }

        for (int currentAmount = 1; currentAmount <= amount; currentAmount++) {
            for (int coin : coins) {
                if (coin > currentAmount) {
                    continue;
                }

                int previousMin = minCoins[currentAmount - coin];
                if (previousMin == unreachable) {
                    continue;
                }

                int candidateMin = previousMin + 1;
                if (candidateMin < minCoins[currentAmount]) {
                    minCoins[currentAmount] = candidateMin;
                }
            }
        }

        return minCoins[amount];
    }
}