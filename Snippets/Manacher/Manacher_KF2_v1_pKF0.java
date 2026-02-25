package com.thealgorithms.strings;

public final class Manacher {

    private Manacher() {
        // Utility class; prevent instantiation
    }

    public static String longestPalindrome(String input) {
        if (input == null || input.isEmpty()) {
            return "";
        }

        final String processed = preprocess(input);
        final int length = processed.length();
        int[] palindromeRadii = new int[length];

        int center = 0;
        int rightBoundary = 0;
        int maxRadius = 0;
        int maxCenter = 0;

        for (int i = 1; i < length - 1; i++) {
            int mirror = 2 * center - i;

            if (i < rightBoundary) {
                palindromeRadii[i] = Math.min(rightBoundary - i, palindromeRadii[mirror]);
            }

            while (processed.charAt(i + 1 + palindromeRadii[i]) == processed.charAt(i - 1 - palindromeRadii[i])) {
                palindromeRadii[i]++;
            }

            int currentRight = i + palindromeRadii[i];
            if (currentRight > rightBoundary) {
                center = i;
                rightBoundary = currentRight;
            }

            if (palindromeRadii[i] > maxRadius) {
                maxRadius = palindromeRadii[i];
                maxCenter = i;
            }
        }

        int startIndex = (maxCenter - maxRadius) / 2;
        return input.substring(startIndex, startIndex + maxRadius);
    }

    private static String preprocess(String input) {
        if (input.isEmpty()) {
            return "^$";
        }

        StringBuilder builder = new StringBuilder("^");
        for (char character : input.toCharArray()) {
            builder.append('#').append(character);
        }
        builder.append("#$");
        return builder.toString();
    }
}