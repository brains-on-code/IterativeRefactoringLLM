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

        // Current rightmost palindrome (center and its right boundary)
        int center = 0;
        int rightBoundary = 0;

        // Best palindrome found so far (radius and center in processed string)
        int maxRadius = 0;
        int maxCenter = 0;

        for (int i = 1; i < n - 1; i++) {
            int mirror = 2 * center - i;

            // Initialize radius using mirror information if within current right boundary
            if (i < rightBoundary) {
                palindromeRadius[i] = Math.min(rightBoundary - i, palindromeRadius[mirror]);
            }

            // Expand palindrome centered at i
            while (processed.charAt(i + 1 + palindromeRadius[i])
                    == processed.charAt(i - 1 - palindromeRadius[i])) {
                palindromeRadius[i]++;
            }

            // Update current rightmost palindrome
            int currentRight = i + palindromeRadius[i];
            if (currentRight > rightBoundary) {
                center = i;
                rightBoundary = currentRight;
            }

            // Track the longest palindrome found so far
            if (palindromeRadius[i] > maxRadius) {
                maxRadius = palindromeRadius[i];
                maxCenter = i;
            }
        }

        int start = (maxCenter - maxRadius) / 2;
        return s.substring(start, start + maxRadius);
    }

    /**
     * Transforms the input string to simplify palindrome expansion.
     *
     * Example:
     *   "abba" -> "^#a#b#b#a#$"
     *
     * Properties:
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