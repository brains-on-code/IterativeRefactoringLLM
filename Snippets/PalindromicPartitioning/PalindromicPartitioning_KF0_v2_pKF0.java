package com.thealgorithms.dynamicprogramming;

/**
 * Provides functionality to solve the Palindrome Partitioning II problem, which involves finding
 * the minimum number of partitions needed to divide a given string into palindromic substrings.
 *
 * <p>
 * The problem is solved using dynamic programming. The approach involves checking all possible
 * substrings and determining whether they are palindromes. The minimum number of cuts required
 * for palindrome partitioning is computed in a bottom-up manner.
 * </p>
 *
 * <p>
 * Example:
 * <ul>
 *     <li>Input: "nitik" => Output: 2 (Partitioning: "n | iti | k")</li>
 *     <li>Input: "ababbbabbababa" => Output: 3 (Partitioning: "aba | b | bbabb | ababa")</li>
 * </ul>
 * </p>
 *
 * @see <a href="https://leetcode.com/problems/palindrome-partitioning-ii/">Palindrome Partitioning II</a>
 * @see <a href="https://www.geeksforgeeks.org/palindrome-partitioning-dp-17/">Palindrome Partitioning (GeeksforGeeks)</a>
 */
public final class PalindromicPartitioning {

    private PalindromicPartitioning() {
        // Utility class; prevent instantiation
    }

    public static int minimalPartitions(String word) {
        if (word == null || word.isEmpty()) {
            return 0;
        }

        int length = word.length();
        boolean[][] isPalindrome = new boolean[length][length];
        int[] minCuts = new int[length];

        precomputePalindromes(word, isPalindrome);
        computeMinCuts(isPalindrome, minCuts);

        return minCuts[length - 1];
    }

    private static void precomputePalindromes(String word, boolean[][] isPalindrome) {
        int length = word.length();

        for (int i = 0; i < length; i++) {
            isPalindrome[i][i] = true;
        }

        for (int subLen = 2; subLen <= length; subLen++) {
            for (int start = 0; start <= length - subLen; start++) {
                int end = start + subLen - 1;
                char startChar = word.charAt(start);
                char endChar = word.charAt(end);

                if (startChar != endChar) {
                    isPalindrome[start][end] = false;
                } else if (subLen == 2) {
                    isPalindrome[start][end] = true;
                } else {
                    isPalindrome[start][end] = isPalindrome[start + 1][end - 1];
                }
            }
        }
    }

    private static void computeMinCuts(boolean[][] isPalindrome, int[] minCuts) {
        int length = minCuts.length;

        for (int end = 0; end < length; end++) {
            if (isPalindrome[0][end]) {
                minCuts[end] = 0;
                continue;
            }

            int bestCut = Integer.MAX_VALUE;
            for (int start = 0; start < end; start++) {
                if (isPalindrome[start + 1][end]) {
                    bestCut = Math.min(bestCut, minCuts[start] + 1);
                }
            }
            minCuts[end] = bestCut;
        }
    }
}