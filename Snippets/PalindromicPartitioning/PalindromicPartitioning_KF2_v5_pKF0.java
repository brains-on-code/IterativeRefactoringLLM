package com.thealgorithms.dynamicprogramming;

public final class PalindromicPartitioning {

    private PalindromicPartitioning() {
        // Utility class; prevent instantiation
    }

    public static int minimalPartitions(String word) {
        if (word == null || word.isEmpty()) {
            return 0;
        }

        boolean[][] isPalindrome = buildPalindromeTable(word);
        int[] minCuts = computeMinCuts(isPalindrome);

        return minCuts[word.length() - 1];
    }

    private static boolean[][] buildPalindromeTable(String word) {
        int length = word.length();
        boolean[][] isPalindrome = new boolean[length][length];

        markSingleCharacterPalindromes(isPalindrome, length);
        markLongerPalindromes(word, isPalindrome, length);

        return isPalindrome;
    }

    private static void markSingleCharacterPalindromes(boolean[][] isPalindrome, int length) {
        for (int i = 0; i < length; i++) {
            isPalindrome[i][i] = true;
        }
    }

    private static void markLongerPalindromes(String word, boolean[][] isPalindrome, int length) {
        for (int subLength = 2; subLength <= length; subLength++) {
            for (int start = 0; start <= length - subLength; start++) {
                int end = start + subLength - 1;

                if (word.charAt(start) != word.charAt(end)) {
                    isPalindrome[start][end] = false;
                    continue;
                }

                isPalindrome[start][end] =
                    (subLength == 2) || isPalindrome[start + 1][end - 1];
            }
        }
    }

    private static int[] computeMinCuts(boolean[][] isPalindrome) {
        int length = isPalindrome.length;
        int[] minCuts = new int[length];

        for (int end = 0; end < length; end++) {
            minCuts[end] = computeMinCutForEndIndex(isPalindrome, minCuts, end);
        }

        return minCuts;
    }

    private static int computeMinCutForEndIndex(boolean[][] isPalindrome, int[] minCuts, int end) {
        if (isPalindrome[0][end]) {
            return 0;
        }

        int bestCut = Integer.MAX_VALUE;
        for (int start = 0; start < end; start++) {
            if (isPalindrome[start + 1][end]) {
                bestCut = Math.min(bestCut, minCuts[start] + 1);
            }
        }
        return bestCut;
    }
}