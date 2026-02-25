package com.thealgorithms.dynamicprogramming;

public final class PalindromicPartitioning {

    private PalindromicPartitioning() {
        // Utility class; prevent instantiation
    }

    public static int minimalPartitions(String word) {
        int length = word.length();
        if (length == 0) {
            return 0;
        }

        boolean[][] isPalindrome = buildPalindromeTable(word);
        int[] minCuts = computeMinCuts(isPalindrome, length);

        return minCuts[length - 1];
    }

    private static boolean[][] buildPalindromeTable(String word) {
        int length = word.length();
        boolean[][] isPalindrome = new boolean[length][length];

        // Single characters are palindromes
        for (int i = 0; i < length; i++) {
            isPalindrome[i][i] = true;
        }

        // Substrings of length >= 2
        for (int subLen = 2; subLen <= length; subLen++) {
            for (int start = 0; start <= length - subLen; start++) {
                int end = start + subLen - 1;
                char startChar = word.charAt(start);
                char endChar = word.charAt(end);

                if (subLen == 2) {
                    isPalindrome[start][end] = (startChar == endChar);
                } else {
                    isPalindrome[start][end] =
                        (startChar == endChar) && isPalindrome[start + 1][end - 1];
                }
            }
        }

        return isPalindrome;
    }

    private static int[] computeMinCuts(boolean[][] isPalindrome, int length) {
        int[] minCuts = new int[length];

        for (int end = 0; end < length; end++) {
            if (isPalindrome[0][end]) {
                minCuts[end] = 0;
                continue;
            }

            int bestCut = Integer.MAX_VALUE;
            for (int start = 0; start < end; start++) {
                if (isPalindrome[start + 1][end] && minCuts[start] + 1 < bestCut) {
                    bestCut = minCuts[start] + 1;
                }
            }
            minCuts[end] = bestCut;
        }

        return minCuts;
    }
}