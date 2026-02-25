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

        markSingleCharacterPalindromes(palindromeTable, length);
        markLongerPalindromes(word, palindromeTable, length);

        return palindromeTable;
    }

    private static void markSingleCharacterPalindromes(boolean[][] palindromeTable, int length) {
        for (int i = 0; i < length; i++) {
            palindromeTable[i][i] = true;
        }
    }

    private static void markLongerPalindromes(String word, boolean[][] palindromeTable, int length) {
        for (int subLength = 2; subLength <= length; subLength++) {
            for (int start = 0; start <= length - subLength; start++) {
                int end = start + subLength - 1;

                if (word.charAt(start) != word.charAt(end)) {
                    palindromeTable[start][end] = false;
                    continue;
                }

                palindromeTable[start][end] =
                    (subLength == 2) || palindromeTable[start + 1][end - 1];
            }
        }
    }

    private static int[] computeMinCuts(boolean[][] palindromeTable) {
        int length = palindromeTable.length;
        int[] minCuts = new int[length];

        for (int end = 0; end < length; end++) {
            minCuts[end] = computeMinCutForEndIndex(palindromeTable, minCuts, end);
        }

        return minCuts;
    }

    private static int computeMinCutForEndIndex(boolean[][] palindromeTable, int[] minCuts, int end) {
        if (palindromeTable[0][end]) {
            return 0;
        }

        int bestCut = Integer.MAX_VALUE;
        for (int start = 0; start < end; start++) {
            if (palindromeTable[start + 1][end]) {
                bestCut = Math.min(bestCut, minCuts[start] + 1);
            }
        }
        return bestCut;
    }
}