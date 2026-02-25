package com.thealgorithms.strings;

/**
 * Utility class for palindrome-related string operations.
 */
public final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Returns the longest palindromic substring of the given string using
     * Manacher's algorithm (O(n) time).
     *
     * @param input the input string
     * @return the longest palindromic substring
     */
    public static String method1(String input) {
        final String processed = preprocess(input);
        int[] palindromeRadii = new int[processed.length()];

        int center = 0;
        int rightBoundary = 0;
        int maxRadius = 0;
        int maxCenter = 0;

        for (int i = 1; i < processed.length() - 1; i++) {
            int mirror = 2 * center - i;

            if (i < rightBoundary) {
                palindromeRadii[i] = Math.min(rightBoundary - i, palindromeRadii[mirror]);
            }

            while (processed.charAt(i + 1 + palindromeRadii[i]) == processed.charAt(i - 1 - palindromeRadii[i])) {
                palindromeRadii[i]++;
            }

            if (i + palindromeRadii[i] > rightBoundary) {
                center = i;
                rightBoundary = i + palindromeRadii[i];
            }

            if (palindromeRadii[i] > maxRadius) {
                maxRadius = palindromeRadii[i];
                maxCenter = i;
            }
        }

        final int startIndex = (maxCenter - maxRadius) / 2;
        return input.substring(startIndex, startIndex + maxRadius);
    }

    /**
     * Preprocesses the input string by inserting boundary characters to
     * handle even-length palindromes uniformly.
     *
     * Example:
     *   "abba" -> "^#a#b#b#a#$"
     *
     * @param input the original string
     * @return the transformed string with boundaries
     */
    private static String preprocess(String input) {
        if (input.isEmpty()) {
            return "^$";
        }
        StringBuilder builder = new StringBuilder("^");
        for (char c : input.toCharArray()) {
            builder.append('#').append(c);
        }
        builder.append("#$");
        return builder.toString();
    }
}