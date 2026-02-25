package com.thealgorithms.dynamicprogramming;

public final class CoinChange {

    private CoinChange() {
        // Utility class; prevent instantiation
    }

    /**
     * Calculates the number of combinations to make up the given amount
     * using the provided coin denominations.
     *
     * @param coins  array of coin denominations
     * @param amount target amount
     * @return number of combinations to form the amount
     */
    public static int change(int[] coins, int amount) {
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
     * Calculates the minimum number of coins required to make up the given amount
     * using the provided coin denominations.
     *
     * @param coins  array of coin denominations
     * @param amount target amount
     * @return minimum number of coins to form the amount, or Integer.MAX_VALUE if not possible
     */
    public static int minimumCoins(int[] coins, int amount) {
        int[] minCoins = new int[amount + 1];

        minCoins[0] = 0;
        for (int currentAmount = 1; currentAmount <= amount; currentAmount++) {
            minCoins[currentAmount] = Integer.MAX_VALUE;
        }

        for (int currentAmount = 1; currentAmount <= amount; currentAmount++) {
            for (int coin : coins) {
                if (coin > currentAmount) {
                    continue;
                }

                int remainingAmount = currentAmount - coin;
                int subResult = minCoins[remainingAmount];

                if (subResult != Integer.MAX_VALUE && subResult + 1 < minCoins[currentAmount]) {
                    minCoins[currentAmount] = subResult + 1;
                }
            }
        }

        return minCoins[amount];
    }
}