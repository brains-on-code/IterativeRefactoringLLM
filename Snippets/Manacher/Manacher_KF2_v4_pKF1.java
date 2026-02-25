package com.thealgorithms.strings;

public final class Manacher {

    private Manacher() {
    }

    public static String longestPalindrome(String original) {
        final String processed = preprocess(original);
        int[] palindromeLengths = new int[processed.length()];
        int center = 0;
        int rightBoundary = 0;
        int maxLength = 0;
        int centerOfMax = 0;

        for (int i = 1; i < processed.length() - 1; i++) {
            int mirror = 2 * center - i;

            if (i < rightBoundary) {
                palindromeLengths[i] =
                    Math.min(rightBoundary - i, palindromeLengths[mirror]);
            }

            while (processed.charAt(i + 1 + palindromeLengths[i])
                    == processed.charAt(i - 1 - palindromeLengths[i])) {
                palindromeLengths[i]++;
            }

            if (i + palindromeLengths[i] > rightBoundary) {
                center = i;
                rightBoundary = i + palindromeLengths[i];
            }

            if (palindromeLengths[i] > maxLength) {
                maxLength = palindromeLengths[i];
                centerOfMax = i;
            }
        }

        final int startIndex = (centerOfMax - maxLength) / 2;
        return original.substring(startIndex, startIndex + maxLength);
    }

    private static String preprocess(String input) {
        if (input.isEmpty()) {
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