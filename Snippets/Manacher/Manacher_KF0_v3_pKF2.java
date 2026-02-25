package com.thealgorithms.strings;

/**
 * Manacher's algorithm for finding the longest palindromic substring.
 *
 * Reference: https://en.wikipedia.org/wiki/Longest_palindromic_substring#Manacher's_algorithm
 */
public final class Manacher {

    private Manacher() {
        // Utility class; prevent instantiation
    }

    /**
     * Returns the longest palindromic substring of the given string using Manacher's algorithm.
     *
     * @param s input string
     * @return longest palindromic substring in {@code s}
     */
    public static String longestPalindrome(String s) {
        final String processed = preprocess(s);
        final int n = processed.length();

        int[] palindromeRadius = new int[n];
        int center = 0;          // Center of the current rightmost palindrome
        int rightBoundary = 0;   // Right boundary of the current rightmost palindrome

        int maxRadius = 0;       // Radius of the longest palindrome found so far
        int maxCenter = 0;       // Center of the longest palindrome found so far

        for (int i = 1; i < n - 1; i++) {
            int mirror = 2 * center - i; // Mirror position of i around current center

            // Initialize radius using previously computed palindrome if within the right boundary
            if (i < rightBoundary) {
                palindromeRadius[i] = Math.min(rightBoundary - i, palindromeRadius[mirror]);
            }

            // Attempt to expand palindrome centered at i
            while (processed.charAt(i + 1 + palindromeRadius[i])
                    == processed.charAt(i - 1 - palindromeRadius[i])) {
                palindromeRadius[i]++;
            }

            // Update center and rightBoundary if the palindrome at i extends beyond rightBoundary
            if (i + palindromeRadius[i] > rightBoundary) {
                center = i;
                rightBoundary = i + palindromeRadius[i];
            }

            // Track the longest palindrome found so far
            if (palindromeRadius[i] > maxRadius) {
                maxRadius = palindromeRadius[i];
                maxCenter = i;
            }
        }

        // Map back to the original string indices
        int start = (maxCenter - maxRadius) / 2;
        return s.substring(start, start + maxRadius);
    }

    /**
     * Transforms the input string to simplify palindrome expansion.
     *
     * Example:
     *   "abba" -> "^#a#b#b#a#$"
     *
     * Properties of the transformed string:
     * - All palindromes have odd length.
     * - Sentinels (^) and ($) prevent bounds checking.
     *
     * @param s original string
     * @return transformed string with sentinels and separators
     */
    private static String preprocess(String s) {
        if (s.isEmpty()) {
            return "^$";
        }

        StringBuilder builder = new StringBuilder("^");
        for (char c : s.toCharArray()) {
            builder.append('#').append(c);
        }
        builder.append("#$");

        return builder.toString();
    }
}