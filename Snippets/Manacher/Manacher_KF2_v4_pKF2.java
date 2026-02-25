package com.thealgorithms.strings;

public final class Manacher {

    private Manacher() {
        // Prevent instantiation of utility class
    }

    /**
     * Finds the longest palindromic substring in the given string using
     * Manacher's algorithm in O(n) time.
     *
     * @param s input string
     * @return longest palindromic substring
     */
    public static String longestPalindrome(String s) {
        final String processed = preprocess(s);
        final int n = processed.length();

        // radius[i] = radius of palindrome centered at i in the processed string
        int[] radius = new int[n];

        // Current rightmost palindrome's center and right boundary
        int center = 0;
        int rightBoundary = 0;

        // Longest palindrome found so far (in processed string coordinates)
        int maxRadius = 0;
        int maxCenter = 0;

        for (int i = 1; i < n - 1; i++) {
            int mirror = 2 * center - i; // Mirror index of i with respect to current center

            // Use mirror's radius if within the current right boundary
            if (i < rightBoundary) {
                radius[i] = Math.min(rightBoundary - i, radius[mirror]);
            }

            // Expand palindrome centered at i
            while (processed.charAt(i + 1 + radius[i]) == processed.charAt(i - 1 - radius[i])) {
                radius[i]++;
            }

            // Update center and rightBoundary if palindrome at i extends beyond current rightBoundary
            if (i + radius[i] > rightBoundary) {
                center = i;
                rightBoundary = i + radius[i];
            }

            // Track the longest palindrome found so far
            if (radius[i] > maxRadius) {
                maxRadius = radius[i];
                maxCenter = i;
            }
        }

        // Convert processed string indices back to original string indices
        int start = (maxCenter - maxRadius) / 2;
        return s.substring(start, start + maxRadius);
    }

    /**
     * Transforms the input string so that palindromes of even and odd length
     * are handled uniformly by inserting separators and sentinels.
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