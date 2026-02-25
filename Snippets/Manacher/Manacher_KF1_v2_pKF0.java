package com.thealgorithms.strings;

/**
 * Utility class for palindrome-related string operations.
 */
public final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Finds the longest palindromic substring in the given string using Manacher's algorithm.
     *
     * @param input the input string
     * @return the longest palindromic substring
     */
    public static String method1(String input) {
        if (input == null || input.isEmpty()) {
            return "";
        }

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

            while (processed.charAt(i + 1 + palindromeRadii[i])
                    == processed.charAt(i - 1 - palindromeRadii[i])) {
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
     * Preprocesses the string by inserting boundary characters to handle even-length palindromes.
     *
     * @param input the original string
     * @return the transformed string with boundaries
     */
    private static String preprocess(String input) {
        if (input == null || input.isEmpty()) {
            return "^$";
        }

        StringBuilder builder = new StringBuilder("^");
        for (char ch : input.toCharArray()) {
            builder.append('#').append(ch);
        }
        builder.append("#$");
        return builder.toString();
    }
}