package com.thealgorithms.dynamicprogramming;

/**
 * Utility class for computing the Longest Common Subsequence (LCS) between two strings.
 */
final class LongestCommonSubsequence {

    private LongestCommonSubsequence() {
        // Utility class; prevent instantiation.
    }

    /**
     * Finds the Longest Common Subsequence (LCS) between two strings.
     *
     * @param firstString  the first input string
     * @param secondString the second input string
     * @return the LCS as a string, or {@code null} if either input is {@code null}
     */
    public static String findLCS(String firstString, String secondString) {
        if (firstString == null || secondString == null) {
            return null;
        }
        if (firstString.isEmpty() || secondString.isEmpty()) {
            return "";
        }

        String[] firstChars = firstString.split("");
        String[] secondChars = secondString.split("");

        int firstLength = firstChars.length;
        int secondLength = secondChars.length;

        int[][] lcsLengthTable = new int[firstLength + 1][secondLength + 1];

        for (int row = 0; row <= firstLength; row++) {
            lcsLengthTable[row][0] = 0;
        }
        for (int col = 0; col <= secondLength; col++) {
            lcsLengthTable[0][col] = 0;
        }

        for (int row = 1; row <= firstLength; row++) {
            for (int col = 1; col <= secondLength; col++) {
                if (firstChars[row - 1].equals(secondChars[col - 1])) {
                    lcsLengthTable[row][col] = lcsLengthTable[row - 1][col - 1] + 1;
                } else {
                    lcsLengthTable[row][col] =
                        Math.max(lcsLengthTable[row - 1][col], lcsLengthTable[row][col - 1]);
                }
            }
        }

        return buildLCS(firstString, secondString, lcsLengthTable);
    }

    /**
     * Reconstructs the Longest Common Subsequence (LCS) from the DP table.
     *
     * @param firstString    the first input string
     * @param secondString   the second input string
     * @param lcsLengthTable the DP table containing LCS lengths
     * @return the reconstructed LCS as a string
     */
    public static String buildLCS(String firstString, String secondString, int[][] lcsLengthTable) {
        StringBuilder lcsBuilder = new StringBuilder();
        int firstIndex = firstString.length();
        int secondIndex = secondString.length();

        while (firstIndex > 0 && secondIndex > 0) {
            char firstChar = firstString.charAt(firstIndex - 1);
            char secondChar = secondString.charAt(secondIndex - 1);

            if (firstChar == secondChar) {
                lcsBuilder.append(firstChar);
                firstIndex--;
                secondIndex--;
            } else if (lcsLengthTable[firstIndex - 1][secondIndex] > lcsLengthTable[firstIndex][secondIndex - 1]) {
                firstIndex--;
            } else {
                secondIndex--;
            }
        }

        return lcsBuilder.reverse().toString();
    }
}