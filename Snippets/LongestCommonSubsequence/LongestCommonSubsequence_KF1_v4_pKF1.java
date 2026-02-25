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
     * @param first  the first input string
     * @param second the second input string
     * @return the LCS as a string, or {@code null} if either input is {@code null}
     */
    public static String findLCS(String first, String second) {
        if (first == null || second == null) {
            return null;
        }
        if (first.isEmpty() || second.isEmpty()) {
            return "";
        }

        String[] firstCharacters = first.split("");
        String[] secondCharacters = second.split("");

        int firstLength = firstCharacters.length;
        int secondLength = secondCharacters.length;

        int[][] lcsLengthTable = new int[firstLength + 1][secondLength + 1];

        for (int rowIndex = 0; rowIndex <= firstLength; rowIndex++) {
            lcsLengthTable[rowIndex][0] = 0;
        }
        for (int columnIndex = 0; columnIndex <= secondLength; columnIndex++) {
            lcsLengthTable[0][columnIndex] = 0;
        }

        for (int rowIndex = 1; rowIndex <= firstLength; rowIndex++) {
            for (int columnIndex = 1; columnIndex <= secondLength; columnIndex++) {
                if (firstCharacters[rowIndex - 1].equals(secondCharacters[columnIndex - 1])) {
                    lcsLengthTable[rowIndex][columnIndex] =
                        lcsLengthTable[rowIndex - 1][columnIndex - 1] + 1;
                } else {
                    lcsLengthTable[rowIndex][columnIndex] =
                        Math.max(lcsLengthTable[rowIndex - 1][columnIndex], lcsLengthTable[rowIndex][columnIndex - 1]);
                }
            }
        }

        return buildLCS(first, second, lcsLengthTable);
    }

    /**
     * Reconstructs the Longest Common Subsequence (LCS) from the DP table.
     *
     * @param first          the first input string
     * @param second         the second input string
     * @param lcsLengthTable the DP table containing LCS lengths
     * @return the reconstructed LCS as a string
     */
    public static String buildLCS(String first, String second, int[][] lcsLengthTable) {
        StringBuilder lcsBuilder = new StringBuilder();
        int firstIndex = first.length();
        int secondIndex = second.length();

        while (firstIndex > 0 && secondIndex > 0) {
            char firstCharacter = first.charAt(firstIndex - 1);
            char secondCharacter = second.charAt(secondIndex - 1);

            if (firstCharacter == secondCharacter) {
                lcsBuilder.append(firstCharacter);
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