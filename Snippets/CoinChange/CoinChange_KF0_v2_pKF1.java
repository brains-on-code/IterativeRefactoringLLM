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
        int[] combinationsForAmount = new int[targetAmount + 1];
        combinationsForAmount[0] = 1;

        for (int coinValue : coinDenominations) {
            for (int currentAmount = coinValue; currentAmount <= targetAmount; currentAmount++) {
                combinationsForAmount[currentAmount] += combinationsForAmount[currentAmount - coinValue];
            }
        }

        return combinationsForAmount[targetAmount];
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
        int[] minimumCoinsForAmount = new int[targetAmount + 1];

        minimumCoinsForAmount[0] = 0;

        for (int amount = 1; amount <= targetAmount; amount++) {
            minimumCoinsForAmount[amount] = Integer.MAX_VALUE;
        }

        for (int currentAmount = 1; currentAmount <= targetAmount; currentAmount++) {
            for (int coinValue : coinDenominations) {
                if (coinValue <= currentAmount) {
                    int remainingAmount = currentAmount - coinValue;
                    int coinsNeededForRemaining = minimumCoinsForAmount[remainingAmount];

                    if (coinsNeededForRemaining != Integer.MAX_VALUE
                            && coinsNeededForRemaining + 1 < minimumCoinsForAmount[currentAmount]) {
                        minimumCoinsForAmount[currentAmount] = coinsNeededForRemaining + 1;
                    }
                }
            }
        }

        return minimumCoinsForAmount[targetAmount];
    }
}