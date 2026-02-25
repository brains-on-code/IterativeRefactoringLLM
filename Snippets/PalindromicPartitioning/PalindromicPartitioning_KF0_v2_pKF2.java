package com.thealgorithms.dynamicprogramming;

/**
 * Solves the Palindrome Partitioning II problem:
 * given a string, find the minimum number of cuts needed
 * to partition it into palindromic substrings.
 *
 * <p>Dynamic programming approach:
 * <ul>
 *     <li>Precompute all palindromic substrings.</li>
 *     <li>Use those results to compute the minimum cuts.</li>
 * </ul>
 *
 * @see <a href="https://leetcode.com/problems/palindrome-partitioning-ii/">Palindrome Partitioning II</a>
 * @see <a href="https://www.geeksforgeeks.org/palindrome-partitioning-dp-17/">Palindrome Partitioning (GeeksforGeeks)</a>
 */
public final class PalindromicPartitioning {

    private PalindromicPartitioning() {
        // Utility class; prevent instantiation
    }

    /**
     * Returns the minimum number of cuts needed to partition the given string
     * into palindromic substrings.
     *
     * @param word input string
     * @return minimum number of cuts
     */
    public static int minimalPartitions(String word) {
        int len = word.length();

        // minCuts[i] = minimum number of cuts needed for word[0..i]
        int[] minCuts = new int[len];

        // isPalindrome[i][j] = true if word[i..j] is a palindrome
        boolean[][] isPalindrome = new boolean[len][len];

        // Base case: every single character is a palindrome
        for (int i = 0; i < len; i++) {
            isPalindrome[i][i] = true;
        }

        // Precompute palindromic substrings of length >= 2
        for (int subLen = 2; subLen <= len; subLen++) {
            for (int start = 0; start <= len - subLen; start++) {
                int end = start + subLen - 1;
                if (subLen == 2) {
                    isPalindrome[start][end] = word.charAt(start) == word.charAt(end);
                } else {
                    isPalindrome[start][end] =
                        word.charAt(start) == word.charAt(end) && isPalindrome[start + 1][end - 1];
                }
            }
        }

        // Compute minimum cuts for each prefix word[0..i]
        for (int i = 0; i < len; i++) {
            if (isPalindrome[0][i]) {
                minCuts[i] = 0;
                continue;
            }

            int min = Integer.MAX_VALUE;
            for (int j = 0; j < i; j++) {
                if (isPalindrome[j + 1][i] && minCuts[j] + 1 < min) {
                    min = minCuts[j] + 1;
                }
            }
            minCuts[i] = min;
        }

        return minCuts[len - 1];
    }
}