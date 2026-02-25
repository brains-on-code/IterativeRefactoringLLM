package com.thealgorithms.strings;

public final class Manacher {

    private Manacher() {
    }

    public static String longestPalindrome(String original) {
        final String processed = preprocessString(original);
        int[] palindromeLengths = new int[processed.length()];
        int center = 0;
        int rightBoundary = 0;
        int maxLength = 0;
        int maxCenter = 0;

        for (int currentIndex = 1; currentIndex < processed.length() - 1; currentIndex++) {
            int mirrorIndex = 2 * center - currentIndex;

            if (currentIndex < rightBoundary) {
                palindromeLengths[currentIndex] =
                    Math.min(rightBoundary - currentIndex, palindromeLengths[mirrorIndex]);
            }

            while (processed.charAt(currentIndex + 1 + palindromeLengths[currentIndex])
                    == processed.charAt(currentIndex - 1 - palindromeLengths[currentIndex])) {
                palindromeLengths[currentIndex]++;
            }

            if (currentIndex + palindromeLengths[currentIndex] > rightBoundary) {
                center = currentIndex;
                rightBoundary = currentIndex + palindromeLengths[currentIndex];
            }

            if (palindromeLengths[currentIndex] > maxLength) {
                maxLength = palindromeLengths[currentIndex];
                maxCenter = currentIndex;
            }
        }

        final int startIndex = (maxCenter - maxLength) / 2;
        return original.substring(startIndex, startIndex + maxLength);
    }

    private static String preprocessString(String input) {
        if (input.isEmpty()) {
            return "^$";
        }
        StringBuilder processed = new StringBuilder("^");
        for (char ch : input.toCharArray()) {
            processed.append('#').append(ch);
        }
        processed.append("#$");
        return processed.toString();
    }
}