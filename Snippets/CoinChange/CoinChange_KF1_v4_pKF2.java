package com.thealgorithms.dynamicprogramming;

/**
 * Utility class for coin change related dynamic programming algorithms.
 */
public final class CoinChangeUtils {

    private CoinChangeUtils() {
        // Utility class; prevent instantiation
    }

    /**
     * Counts the number of distinct combinations of coins that sum to the given amount.
     * Order of coins does not matter.
     *
     * @param coins  array of coin denominations
     * @param amount target amount
     * @return number of combinations to form the target amount
     */
    public static int countCombinations(int[] coins, int amount) {
        int[] ways = new int[amount + 1];

        // Base case: one way to make amount 0 (use no coins)
        ways[0] = 1;

        for (int coin : coins) {
            for (int currentAmount = coin; currentAmount <= amount; currentAmount++) {
                ways[currentAmount] += ways[currentAmount - coin];
            }
        }

        return ways[amount];
    }

    /**
     * Computes the minimum number of coins needed to make up a given amount using
     * the provided coin denominations. If the amount cannot be formed, returns
     * Integer.MAX_VALUE.
     *
     * @param coins  array of coin denominations
     * @param amount target amount
     * @return minimum number of coins to form the target amount, or
     *         Integer.MAX_VALUE if impossible
     */
    public static int minCoins(int[] coins, int amount) {
        int[] minCoins = new int[amount + 1];

        // Base case: zero coins are needed to make amount 0
        minCoins[0] = 0;

        // Initialize all other amounts as unreachable
        for (int currentAmount = 1; currentAmount <= amount; currentAmount++) {
            minCoins[currentAmount] = Integer.MAX_VALUE;
        }

        for (int currentAmount = 1; currentAmount <= amount; currentAmount++) {
            for (int coin : coins) {
                if (coin > currentAmount) {
                    continue;
                }

                int previous = minCoins[currentAmount - coin];
                if (previous == Integer.MAX_VALUE) {
                    continue;
                }

                int candidate = previous + 1;
                if (candidate < minCoins[currentAmount]) {
                    minCoins[currentAmount] = candidate;
                }
            }
        }

        return minCoins[amount];
    }
}