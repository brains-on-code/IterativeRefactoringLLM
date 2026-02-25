package com.thealgorithms.dynamicprogramming;

/**
 * Utility class for computing the Longest Common Subsequence (LCS) between two strings.
 */
final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    /**
     * Computes the Longest Common Subsequence (LCS) of two strings.
     *
     * @param first  the first input string
     * @param second the second input string
     * @return the LCS of {@code first} and {@code second};
     *         {@code null} if either input is {@code null};
     *         empty string if either input is empty
     */
    public static String method1(String first, String second) {
        if (first == null || second == null) {
            return null;
        }
        if (first.isEmpty() || second.isEmpty()) {
            return "";
        }

        String[] firstChars = first.split("");
        String[] secondChars = second.split("");

        /*
         * dp[i][j] stores the length of the LCS of:
         *   first[0..i-1] and second[0..j-1]
         */
        int[][] dp = new int[firstChars.length + 1][secondChars.length + 1];

        // Fill DP table
        for (int i = 1; i <= firstChars.length; i++) {
            for (int j = 1; j <= secondChars.length; j++) {
                if (firstChars[i - 1].equals(secondChars[j - 1])) {
                    // Current characters match: extend the previous LCS
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    // Current characters do not match: skip one character
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        return method2(first, second, dp);
    }

    /**
     * Reconstructs the Longest Common Subsequence (LCS) from the DP table.
     *
     * @param first  the first input string
     * @param second the second input string
     * @param dp     the DP table containing LCS lengths
     * @return the reconstructed LCS string
     */
    public static String method2(String first, String second, int[][] dp) {
        StringBuilder lcsBuilder = new StringBuilder();
        int i = first.length();
        int j = second.length();

        // Walk backwards through the DP table to reconstruct the LCS
        while (i > 0 && j > 0) {
            if (first.charAt(i - 1) == second.charAt(j - 1)) {
                // Characters are part of the LCS
                lcsBuilder.append(first.charAt(i - 1));
                i--;
                j--;
            } else if (dp[i - 1][j] > dp[i][j - 1]) {
                // Move towards the larger LCS length
                i--;
            } else {
                j--;
            }
        }

        return lcsBuilder.reverse().toString();
    }
}