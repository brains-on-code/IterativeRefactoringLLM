package com.thealgorithms.dynamicprogramming;

public final class PalindromicPartitioning {

    private PalindromicPartitioning() {
        // Utility class; prevent instantiation
    }

    public static int minimalPartitions(String word) {
        if (word == null || word.isEmpty()) {
            return 0;
        }

        boolean[][] palindromeTable = buildPalindromeTable(word);
        int[] minCuts = computeMinCuts(palindromeTable);

        return minCuts[word.length() - 1];
    }

    private static boolean[][] buildPalindromeTable(String word) {
        int length = word.length();
        boolean[][] palindromeTable = new boolean[length][length];

        // Single characters are palindromes
        for (int i = 0; i < length; i++) {
            palindromeTable[i][i] = true;
        }

        // Substrings of length >= 2
        for (int subLength = 2; subLength <= length; subLength++) {
            for (int start = 0; start <= length - subLength; start++) {
                int end = start + subLength - 1;

                if (word.charAt(start) != word.charAt(end)) {
                    palindromeTable[start][end] = false;
                    continue;
                }

                palindromeTable[start][end] = (subLength == 2) || palindromeTable[start + 1][end - 1];
            }
        }

        return palindromeTable;
    }

    private static int[] computeMinCuts(boolean[][] palindromeTable) {
        int length = palindromeTable.length;
        int[] minCuts = new int[length];

        for (int end = 0; end < length; end++) {
            if (palindromeTable[0][end]) {
                minCuts[end] = 0;
                continue;
            }

            int bestCut = Integer.MAX_VALUE;
            for (int start = 0; start < end; start++) {
                if (palindromeTable[start + 1][end]) {
                    bestCut = Math.min(bestCut, minCuts[start] + 1);
                }
            }
            minCuts[end] = bestCut;
        }

        return minCuts;
    }
}