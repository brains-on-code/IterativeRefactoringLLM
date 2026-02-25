package com.thealgorithms.dynamicprogramming;

public final class PalindromicPartitioning {

    private PalindromicPartitioning() {
    }

    public static int minimalPartitions(String word) {
        int length = word.length();

        int[] minCuts = new int[length];
        boolean[][] isPalindrome = new boolean[length][length];

        for (int start = 0; start < length; start++) {
            isPalindrome[start][start] = true;
        }

        for (int subLength = 2; subLength <= length; subLength++) {
            for (int start = 0; start <= length - subLength; start++) {
                int end = start + subLength - 1;
                if (subLength == 2) {
                    isPalindrome[start][end] = (word.charAt(start) == word.charAt(end));
                } else {
                    isPalindrome[start][end] =
                        (word.charAt(start) == word.charAt(end)) && isPalindrome[start + 1][end - 1];
                }
            }
        }

        for (int end = 0; end < length; end++) {
            if (isPalindrome[0][end]) {
                minCuts[end] = 0;
            } else {
                minCuts[end] = Integer.MAX_VALUE;
                for (int partition = 0; partition < end; partition++) {
                    if (isPalindrome[partition + 1][end] && 1 + minCuts[partition] < minCuts[end]) {
                        minCuts[end] = 1 + minCuts[partition];
                    }
                }
            }
        }

        return minCuts[length - 1];
    }
}