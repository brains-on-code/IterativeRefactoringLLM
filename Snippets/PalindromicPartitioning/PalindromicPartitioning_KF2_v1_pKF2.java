package com.thealgorithms.dynamicprogramming;

public final class PalindromicPartitioning {

    private PalindromicPartitioning() {
        // Utility class; prevent instantiation
    }

    /**
     * Returns the minimum number of cuts needed to partition the given string
     * into substrings such that each substring is a palindrome.
     *
     * @param word input string
     * @return minimum number of cuts required
     */
    public static int minimalPartitions(String word) {
        int n = word.length();

        // minCuts[i] = minimum cuts needed for substring word[0..i]
        int[] minCuts = new int[n];

        // isPalindrome[i][j] = true if substring word[i..j] is a palindrome
        boolean[][] isPalindrome = new boolean[n][n];

        // Every single character is a palindrome
        for (int i = 0; i < n; i++) {
            isPalindrome[i][i] = true;
        }

        // Fill palindrome table for all substring lengths >= 2
        for (int subLen = 2; subLen <= n; subLen++) {
            for (int start = 0; start <= n - subLen; start++) {
                int end = start + subLen - 1;

                if (subLen == 2) {
                    isPalindrome[start][end] = (word.charAt(start) == word.charAt(end));
                } else {
                    isPalindrome[start][end] =
                        (word.charAt(start) == word.charAt(end)) && isPalindrome[start + 1][end - 1];
                }
            }
        }

        // Compute minimum cuts using the palindrome table
        for (int end = 0; end < n; end++) {
            if (isPalindrome[0][end]) {
                // No cut needed if the whole prefix is a palindrome
                minCuts[end] = 0;
            } else {
                int best = Integer.MAX_VALUE;
                for (int cut = 0; cut < end; cut++) {
                    if (isPalindrome[cut + 1][end] && 1 + minCuts[cut] < best) {
                        best = 1 + minCuts[cut];
                    }
                }
                minCuts[end] = best;
            }
        }

        return minCuts[n - 1];
    }
}