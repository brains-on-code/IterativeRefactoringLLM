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
        int[] waysToMakeAmount = new int[targetAmount + 1];
        waysToMakeAmount[0] = 1;

        for (int coinValue : coinDenominations) {
            for (int currentAmount = coinValue; currentAmount <= targetAmount; currentAmount++) {
                waysToMakeAmount[currentAmount] += waysToMakeAmount[currentAmount - coinValue];
            }
        }

        return waysToMakeAmount[targetAmount];
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
        int[] minCoinsForAmount = new int[targetAmount + 1];
        minCoinsForAmount[0] = 0;

        for (int amount = 1; amount <= targetAmount; amount++) {
            minCoinsForAmount[amount] = Integer.MAX_VALUE;
        }

        for (int currentAmount = 1; currentAmount <= targetAmount; currentAmount++) {
            for (int coinValue : coinDenominations) {
                if (coinValue <= currentAmount) {
                    int minCoinsForRemaining = minCoinsForAmount[currentAmount - coinValue];
                    if (minCoinsForRemaining != Integer.MAX_VALUE
                            && minCoinsForRemaining + 1 < minCoinsForAmount[currentAmount]) {
                        minCoinsForAmount[currentAmount] = minCoinsForRemaining + 1;
                    }
                }
            }
        }

        return minCoinsForAmount[targetAmount];
    }
}