package com.thealgorithms.dynamicprogramming;

public final class CoinChange {

    private CoinChange() {
        // Utility class; prevent instantiation
    }

    /**
     * Computes the number of distinct combinations of coins that sum to {@code amount}.
     * Order does not matter (e.g., [1, 2] and [2, 1] are considered the same combination).
     *
     * @param coins  available coin denominations
     * @param amount target amount
     * @return number of combinations that form {@code amount}
     */
    public static int change(int[] coins, int amount) {
        int[] combinations = new int[amount + 1];

        // Base case: one way to form amount 0 (use no coins)
        combinations[0] = 1;

        for (int coin : coins) {
            for (int currentAmount = coin; currentAmount <= amount; currentAmount++) {
                combinations[currentAmount] += combinations[currentAmount - coin];
            }
        }

        return combinations[amount];
    }

    /**
     * Computes the minimum number of coins required to form {@code amount}.
     * If {@code amount} cannot be formed with the given coins, returns {@link Integer#MAX_VALUE}.
     *
     * @param coins  available coin denominations
     * @param amount target amount
     * @return minimum number of coins to form {@code amount}, or {@link Integer#MAX_VALUE} if impossible
     */
    public static int minimumCoins(int[] coins, int amount) {
        int[] minCoins = new int[amount + 1];

        // Base case: zero coins are needed to form amount 0
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

                int remainingAmount = currentAmount - coin;
                int previousMin = minCoins[remainingAmount];

                // Update only if the remaining amount is reachable
                if (previousMin != Integer.MAX_VALUE && previousMin + 1 < minCoins[currentAmount]) {
                    minCoins[currentAmount] = previousMin + 1;
                }
            }
        }

        return minCoins[amount];
    }
}