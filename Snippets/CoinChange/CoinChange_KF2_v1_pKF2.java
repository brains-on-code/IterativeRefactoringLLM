package com.thealgorithms.dynamicprogramming;

public final class CoinChange {

    private CoinChange() {
        // Utility class; prevent instantiation
    }

    /**
     * Counts the number of distinct combinations of coins that sum to the given amount.
     * Order of coins does not matter (i.e., [1,2] is the same as [2,1]).
     *
     * @param coins  available coin denominations
     * @param amount target amount
     * @return number of combinations to make up the amount
     */
    public static int change(int[] coins, int amount) {
        int[] combinations = new int[amount + 1];

        // Base case: one way to make amount 0 (choose no coins)
        combinations[0] = 1;

        // For each coin, update the number of ways to form each amount
        for (int coin : coins) {
            for (int currentAmount = coin; currentAmount <= amount; currentAmount++) {
                combinations[currentAmount] += combinations[currentAmount - coin];
            }
        }

        return combinations[amount];
    }

    /**
     * Computes the minimum number of coins required to make the given amount.
     * If the amount cannot be formed with the given coins, returns Integer.MAX_VALUE.
     *
     * @param coins  available coin denominations
     * @param amount target amount
     * @return minimum number of coins to make up the amount, or Integer.MAX_VALUE if impossible
     */
    public static int minimumCoins(int[] coins, int amount) {
        int[] minCoins = new int[amount + 1];

        // Base case: 0 coins are needed to make amount 0
        minCoins[0] = 0;

        // Initialize all other amounts as "infinite" (unreachable)
        for (int currentAmount = 1; currentAmount <= amount; currentAmount++) {
            minCoins[currentAmount] = Integer.MAX_VALUE;
        }

        // Build up the solution for each amount from 1 to amount
        for (int currentAmount = 1; currentAmount <= amount; currentAmount++) {
            for (int coin : coins) {
                if (coin <= currentAmount) {
                    int remainingAmount = currentAmount - coin;
                    int subResult = minCoins[remainingAmount];

                    // Only update if the sub-amount is reachable
                    if (subResult != Integer.MAX_VALUE && subResult + 1 < minCoins[currentAmount]) {
                        minCoins[currentAmount] = subResult + 1;
                    }
                }
            }
        }

        return minCoins[amount];
    }
}