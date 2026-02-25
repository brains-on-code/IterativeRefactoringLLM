package com.thealgorithms.dynamicprogramming;

/**
 * Computes the minimum number of cuts needed to partition a string into
 * palindromic substrings (Palindrome Partitioning II).
 *
 * <p>Approach:
 * <ul>
 *     <li>Precompute all palindromic substrings using dynamic programming.</li>
 *     <li>Use those results to compute the minimum cuts for each prefix.</li>
 * </ul>
 *
 * @see <a href="https://leetcode.com/problems/palindrome-partitioning-ii/">Palindrome Partitioning II</a>
 * @see <a href="https://www.geeksforgeeks.org/palindrome-partitioning-dp-17/">Palindrome Partitioning (GeeksforGeeks)</a>
 */
public final class PalindromicPartitioning {

    private PalindromicPartitioning() {
        // Prevent instantiation of utility class.
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

        // minCuts[i] = minimum number of cuts needed for substring word[0..i]
        int[] minCuts = new int[len];

        // isPalindrome[i][j] = true if substring word[i..j] is a palindrome
        boolean[][] isPalindrome = new boolean[len][len];

        markSingleCharPalindromes(isPalindrome, len);
        markMultiCharPalindromes(word, isPalindrome, len);
        computeMinCuts(isPalindrome, minCuts, len);

        return minCuts[len - 1];
    }

    /**
     * Marks all single-character substrings as palindromes.
     *
     * @param isPalindrome palindrome lookup table
     * @param len length of the input string
     */
    private static void markSingleCharPalindromes(boolean[][] isPalindrome, int len) {
        for (int i = 0; i < len; i++) {
            isPalindrome[i][i] = true;
        }
    }

    /**
     * Precomputes palindromic substrings of length >= 2.
     *
     * @param word input string
     * @param isPalindrome palindrome lookup table
     * @param len length of the input string
     */
    private static void markMultiCharPalindromes(
        String word,
        boolean[][] isPalindrome,
        int len
    ) {
        for (int subLen = 2; subLen <= len; subLen++) {
            for (int start = 0; start <= len - subLen; start++) {
                int end = start + subLen - 1;

                boolean charsMatch = word.charAt(start) == word.charAt(end);
                if (subLen == 2) {
                    isPalindrome[start][end] = charsMatch;
                } else {
                    isPalindrome[start][end] = charsMatch && isPalindrome[start + 1][end - 1];
                }
            }
        }
    }

    /**
     * Computes the minimum number of cuts for each prefix word[0..end].
     *
     * @param isPalindrome palindrome lookup table
     * @param minCuts array to store minimum cuts for each prefix
     * @param len length of the input string
     */
    private static void computeMinCuts(
        boolean[][] isPalindrome,
        int[] minCuts,
        int len
    ) {
        for (int end = 0; end < len; end++) {
            if (isPalindrome[0][end]) {
                minCuts[end] = 0;
                continue;
            }

            int best = Integer.MAX_VALUE;
            for (int cutPos = 0; cutPos < end; cutPos++) {
                if (isPalindrome[cutPos + 1][end]) {
                    best = Math.min(best, minCuts[cutPos] + 1);
                }
            }
            minCuts[end] = best;
        }
    }
}