package com.thealgorithms.dynamicprogramming;

/**
 * Utility class for computing the Longest Common Subsequence (LCS) between two strings.
 */
final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Computes the Longest Common Subsequence (LCS) of two strings.
     *
     * @param first  the first input string
     * @param second the second input string
     * @return the LCS of {@code first} and {@code second}, or {@code null} if either is {@code null}
     */
    public static String method1(String first, String second) {
        if (first == null || second == null) {
            return null;
        }
        if (first.isEmpty() || second.isEmpty()) {
            return "";
        }

        char[] firstChars = first.toCharArray();
        char[] secondChars = second.toCharArray();

        int[][] lcsLengths = new int[firstChars.length + 1][secondChars.length + 1];

        for (int i = 0; i <= firstChars.length; i++) {
            lcsLengths[i][0] = 0;
        }
        for (int j = 0; j <= secondChars.length; j++) {
            lcsLengths[0][j] = 0;
        }

        for (int i = 1; i <= firstChars.length; i++) {
            for (int j = 1; j <= secondChars.length; j++) {
                if (firstChars[i - 1] == secondChars[j - 1]) {
                    lcsLengths[i][j] = lcsLengths[i - 1][j - 1] + 1;
                } else {
                    lcsLengths[i][j] = Math.max(lcsLengths[i - 1][j], lcsLengths[i][j - 1]);
                }
            }
        }

        return method2(first, second, lcsLengths);
    }

    /**
     * Reconstructs the Longest Common Subsequence (LCS) from the DP table.
     *
     * @param first      the first input string
     * @param second     the second input string
     * @param lcsLengths the DP table containing LCS lengths
     * @return the reconstructed LCS
     */
    public static String method2(String first, String second, int[][] lcsLengths) {
        StringBuilder lcsBuilder = new StringBuilder();
        int i = first.length();
        int j = second.length();

        while (i > 0 && j > 0) {
            if (first.charAt(i - 1) == second.charAt(j - 1)) {
                lcsBuilder.append(first.charAt(i - 1));
                i--;
                j--;
            } else if (lcsLengths[i - 1][j] > lcsLengths[i][j - 1]) {
                i--;
            } else {
                j--;
            }
        }

        return lcsBuilder.reverse().toString();
    }
}