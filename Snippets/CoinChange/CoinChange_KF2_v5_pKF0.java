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
        if (!isValidInput(coins, amount)) {
            return 0;
        }

        int[] combinations = new int[amount + 1];
        combinations[0] = 1;

        for (int coin : coins) {
            addCombinationsForCoin(combinations, coin, amount);
        }

        return combinations[amount];
    }

    private static void addCombinationsForCoin(int[] combinations, int coin, int amount) {
        for (int currentAmount = coin; currentAmount <= amount; currentAmount++) {
            combinations[currentAmount] += combinations[currentAmount - coin];
        }
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
        if (!isValidInput(coins, amount)) {
            return Integer.MAX_VALUE;
        }

        int[] minCoins = createInitializedMinCoinsArray(amount);

        for (int currentAmount = 1; currentAmount <= amount; currentAmount++) {
            minCoins[currentAmount] = computeMinCoinsForAmount(coins, minCoins, currentAmount);
        }

        return minCoins[amount];
    }

    private static int[] createInitializedMinCoinsArray(int amount) {
        int[] minCoins = new int[amount + 1];
        minCoins[0] = 0;
        for (int i = 1; i <= amount; i++) {
            minCoins[i] = Integer.MAX_VALUE;
        }
        return minCoins;
    }

    private static int computeMinCoinsForAmount(int[] coins, int[] minCoins, int currentAmount) {
        int best = Integer.MAX_VALUE;

        for (int coin : coins) {
            if (coin > currentAmount) {
                continue;
            }

            int remainingAmount = currentAmount - coin;
            int coinsNeededForRemaining = minCoins[remainingAmount];

            if (coinsNeededForRemaining == Integer.MAX_VALUE) {
                continue;
            }

            int candidate = coinsNeededForRemaining + 1;
            if (candidate < best) {
                best = candidate;
            }
        }

        return best;
    }

    private static boolean isValidInput(int[] coins, int amount) {
        return amount >= 0 && coins != null && coins.length > 0;
    }
}