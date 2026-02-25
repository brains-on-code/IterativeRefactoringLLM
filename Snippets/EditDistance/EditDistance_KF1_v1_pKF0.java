package com.thealgorithms.dynamicprogramming;

/**
 * Utility class for computing Levenshtein (edit) distance between two strings.
 */
public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    /**
     * Computes the Levenshtein distance between two strings using
     * bottom-up dynamic programming.
     *
     * @param source the first string
     * @param target the second string
     * @return the edit distance between {@code source} and {@code target}
     */
    public static int method1(String source, String target) {
        int sourceLength = source.length();
        int targetLength = target.length();

        int[][] dp = new int[sourceLength + 1][targetLength + 1];

        for (int i = 0; i <= sourceLength; i++) {
            dp[i][0] = i;
        }

        for (int j = 0; j <= targetLength; j++) {
            dp[0][j] = j;
        }

        for (int i = 0; i < sourceLength; i++) {
            char sourceChar = source.charAt(i);
            for (int j = 0; j < targetLength; j++) {
                char targetChar = target.charAt(j);

                if (sourceChar == targetChar) {
                    dp[i + 1][j + 1] = dp[i][j];
                } else {
                    int substitutionCost = dp[i][j] + 1;
                    int insertionCost = dp[i][j + 1] + 1;
                    int deletionCost = dp[i + 1][j] + 1;

                    int minCost = Math.min(substitutionCost, insertionCost);
                    minCost = Math.min(deletionCost, minCost);

                    dp[i + 1][j + 1] = minCost;
                }
            }
        }

        return dp[sourceLength][targetLength];
    }

    /**
     * Computes the Levenshtein distance between two strings using
     * top-down recursion with memoization.
     *
     * @param source the first string
     * @param target the second string
     * @return the edit distance between {@code source} and {@code target}
     */
    public static int method3(String source, String target) {
        int[][] memo = new int[source.length() + 1][target.length() + 1];
        return method3(source, target, memo);
    }

    /**
     * Helper for the recursive Levenshtein distance computation.
     *
     * @param source the first string
     * @param target the second string
     * @param memo   memoization table
     * @return the edit distance between {@code source} and {@code target}
     */
    public static int method3(String source, String target, int[][] memo) {
        int sourceLength = source.length();
        int targetLength = target.length();

        if (memo[sourceLength][targetLength] > 0) {
            return memo[sourceLength][targetLength];
        }

        if (sourceLength == 0) {
            memo[sourceLength][targetLength] = targetLength;
            return memo[sourceLength][targetLength];
        }

        if (targetLength == 0) {
            memo[sourceLength][targetLength] = sourceLength;
            return memo[sourceLength][targetLength];
        }

        if (source.charAt(0) == target.charAt(0)) {
            memo[sourceLength][targetLength] =
                method3(source.substring(1), target.substring(1), memo);
        } else {
            int insertCost = method3(source, target.substring(1), memo);
            int deleteCost = method3(source.substring(1), target, memo);
            int replaceCost = method3(source.substring(1), target.substring(1), memo);

            memo[sourceLength][targetLength] =
                1 + Math.min(insertCost, Math.min(deleteCost, replaceCost));
        }

        return memo[sourceLength][targetLength];
    }
}