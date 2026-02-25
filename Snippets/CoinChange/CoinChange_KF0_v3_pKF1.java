package com.thealgorithms.dynamicprogramming;

/**
 * Utility class for coin change problems.
 */
public final class CoinChange {

    private CoinChange() {
        // Prevent instantiation
    }

    /**
     * Calculates the number of distinct combinations of coins that sum up to the given amount.
     *
     * @param coinDenominations the available coin denominations
     * @param targetAmount      the amount for which to compute the number of combinations
     * @return the number of combinations to form {@code targetAmount}
     */
    public static int countCombinations(int[] coinDenominations, int targetAmount) {
        int[] combinationCounts = new int[targetAmount + 1];
        combinationCounts[0] = 1;

        for (int coin : coinDenominations) {
            for (int amount = coin; amount <= targetAmount; amount++) {
                combinationCounts[amount] += combinationCounts[amount - coin];
            }
        }

        return combinationCounts[targetAmount];
    }

    /**
     * Finds the minimum number of coins required to make up the given amount.
     *
     * @param coinDenominations the available coin denominations
     * @param targetAmount      the amount for which to compute the minimum number of coins
     * @return the minimum number of coins needed to form {@code targetAmount},
     *         or {@code Integer.MAX_VALUE} if it is not possible
     */
    public static int findMinimumCoins(int[] coinDenominations, int targetAmount) {
        int[] minCoinsRequired = new int[targetAmount + 1];

        minCoinsRequired[0] = 0;

        for (int amount = 1; amount <= targetAmount; amount++) {
            minCoinsRequired[amount] = Integer.MAX_VALUE;
        }

        for (int amount = 1; amount <= targetAmount; amount++) {
            for (int coin : coinDenominations) {
                if (coin <= amount) {
                    int remainingAmount = amount - coin;
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