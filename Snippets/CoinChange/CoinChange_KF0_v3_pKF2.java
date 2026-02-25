package com.thealgorithms.dynamicprogramming;

/**
 * Utility class for coin change problems using dynamic programming.
 */
public final class CoinChange {

    private CoinChange() {
        // Utility class; prevent instantiation.
    }

    /**
     * Computes the number of distinct combinations of coins that sum to the given amount.
     * Order of coins does not matter (i.e., [1,2] is the same as [2,1]).
     *
     * @param coins  available coin denominations
     * @param amount target amount
     * @return number of combinations to form {@code amount}
     */
    public static int change(int[] coins, int amount) {
        int[] combinations = new int[amount + 1];

        // Base case: one way to make amount 0 (use no coins).
        combinations[0] = 1;

        for (int coin : coins) {
            for (int currentAmount = coin; currentAmount <= amount; currentAmount++) {
                combinations[currentAmount] += combinations[currentAmount - coin];
            }
        }

        return combinations[amount];
    }

    /**
     * Computes the minimum number of coins required to make the given amount.
     *
     * @param coins  available coin denominations
     * @param amount target amount
     * @return minimum number of coins to form {@code amount},
     *         or {@code Integer.MAX_VALUE} if it is not possible
     */
    public static int minimumCoins(int[] coins, int amount) {
        int[] minimumCoins = new int[amount + 1];

        // Base case: zero coins are needed to make amount 0.
        minimumCoins[0] = 0;

        // Initialize all other amounts as unreachable.
        for (int currentAmount = 1; currentAmount <= amount; currentAmount++) {
            minimumCoins[currentAmount] = Integer.MAX_VALUE;
        }

        for (int currentAmount = 1; currentAmount <= amount; currentAmount++) {
            for (int coin : coins) {
                if (coin > currentAmount) {
                    continue;
                }

                int previousAmount = currentAmount - coin;
                int previousMin = minimumCoins[previousAmount];

                if (previousMin == Integer.MAX_VALUE) {
                    continue;
                }

                int candidate = previousMin + 1;
                if (candidate < minimumCoins[currentAmount]) {
                    minimumCoins[currentAmount] = candidate;
                }
            }
        }

        return minimumCoins[amount];
    }
}