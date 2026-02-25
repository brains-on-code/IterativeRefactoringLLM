package com.thealgorithms.dynamicprogramming;

/**
 * Utility class for coin change dynamic programming algorithms.
 */
public final class CoinChange {

    private CoinChange() {
        // Prevent instantiation
    }

    /**
     * Counts the number of distinct combinations of the given coin denominations
     * that sum up to the target amount. Order of coins does not matter.
     *
     * @param coinDenominations array of available coin denominations
     * @param targetAmount      the total amount to form
     * @return number of combinations to form the target amount
     */
    public static int countCombinations(int[] coinDenominations, int targetAmount) {
        int[] combinationCounts = new int[targetAmount + 1];
        combinationCounts[0] = 1;

        for (int coin : coinDenominations) {
            for (int currentAmount = coin; currentAmount <= targetAmount; currentAmount++) {
                combinationCounts[currentAmount] += combinationCounts[currentAmount - coin];
            }
        }

        return combinationCounts[targetAmount];
    }

    /**
     * Computes the minimum number of coins needed to make up the target amount
     * using the given coin denominations. If the amount cannot be formed, the
     * result will be Integer.MAX_VALUE.
     *
     * @param coinDenominations array of available coin denominations
     * @param targetAmount      the total amount to form
     * @return minimum number of coins required to form the target amount,
     *         or Integer.MAX_VALUE if it is not possible
     */
    public static int minCoins(int[] coinDenominations, int targetAmount) {
        int[] minCoinsRequired = new int[targetAmount + 1];
        minCoinsRequired[0] = 0;

        for (int amount = 1; amount <= targetAmount; amount++) {
            minCoinsRequired[amount] = Integer.MAX_VALUE;
        }

        for (int amount = 1; amount <= targetAmount; amount++) {
            for (int coin : coinDenominations) {
                if (coin <= amount) {
                    int previousMin = minCoinsRequired[amount - coin];
                    if (previousMin != Integer.MAX_VALUE && previousMin + 1 < minCoinsRequired[amount]) {
                        minCoinsRequired[amount] = previousMin + 1;
                    }
                }
            }
        }

        return minCoinsRequired[targetAmount];
    }
}