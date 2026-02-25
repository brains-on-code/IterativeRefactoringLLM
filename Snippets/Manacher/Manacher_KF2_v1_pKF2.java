package com.thealgorithms.strings;

public final class Manacher {

    private Manacher() {
        // Utility class; prevent instantiation
    }

    /**
     * Returns the longest palindromic substring in the given string using
     * Manacher's algorithm in O(n) time.
     *
     * @param s input string
     * @return longest palindromic substring
     */
    public static String longestPalindrome(String s) {
        final String processed = preprocess(s);
        final int n = processed.length();

        int[] palindromeRadius = new int[n];
        int center = 0;
        int rightBoundary = 0;

        int maxRadius = 0;
        int maxCenter = 0;

        for (int i = 1; i < n - 1; i++) {
            int mirror = 2 * center - i;

            // Initialize radius using mirror information if within current right boundary
            if (i < rightBoundary) {
                palindromeRadius[i] = Math.min(rightBoundary - i, palindromeRadius[mirror]);
            }

            // Expand around center i while characters match
            while (processed.charAt(i + 1 + palindromeRadius[i])
                    == processed.charAt(i - 1 - palindromeRadius[i])) {
                palindromeRadius[i]++;
            }

            // Update center and right boundary if the palindrome at i extends beyond current boundary
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

        // Map back to original string indices
        int start = (maxCenter - maxRadius) / 2;
        return s.substring(start, start + maxRadius);
    }

    /**
     * Transforms the input string by inserting separators and sentinels to
     * handle even-length palindromes uniformly.
     *
     * Example:
     *   "abba" -> "^#a#b#b#a#$"
     *
     * @param s input string
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