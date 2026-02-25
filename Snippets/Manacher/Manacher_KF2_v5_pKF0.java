package com.thealgorithms.strings;

public final class Manacher {

    private static final char BOUNDARY_START = '^';
    private static final char BOUNDARY_END = '$';
    private static final char SEPARATOR = '#';

    private Manacher() {
        // Utility class; prevent instantiation
    }

    public static String longestPalindrome(String input) {
        if (input == null || input.isEmpty()) {
            return "";
        }

        String processed = preprocess(input);
        int processedLength = processed.length();
        int[] palindromeRadii = new int[processedLength];

        int center = 0;
        int rightBoundary = 0;
        int maxRadius = 0;
        int maxCenter = 0;

        for (int i = 1; i < processedLength - 1; i++) {
            int mirrorIndex = 2 * center - i;

            if (i < rightBoundary) {
                palindromeRadii[i] = Math.min(rightBoundary - i, palindromeRadii[mirrorIndex]);
            }

            while (processed.charAt(i + 1 + palindromeRadii[i]) == processed.charAt(i - 1 - palindromeRadii[i])) {
                palindromeRadii[i]++;
            }

            int currentRightBoundary = i + palindromeRadii[i];
            if (currentRightBoundary > rightBoundary) {
                center = i;
                rightBoundary = currentRightBoundary;
            }

            if (palindromeRadii[i] > maxRadius) {
                maxRadius = palindromeRadii[i];
                maxCenter = i;
            }
        }

        int startIndex = (maxCenter - maxRadius) / 2;
        int endIndex = startIndex + maxRadius;
        return input.substring(startIndex, endIndex);
    }

    private static String preprocess(String input) {
        if (input.isEmpty()) {
            return "" + BOUNDARY_START + BOUNDARY_END;
        }

        StringBuilder builder = new StringBuilder(input.length() * 2 + 3);
        builder.append(BOUNDARY_START);
        for (char character : input.toCharArray()) {
            builder.append(SEPARATOR).append(character);
        }
        builder.append(SEPARATOR).append(BOUNDARY_END);
        return builder.toString();
    }
}