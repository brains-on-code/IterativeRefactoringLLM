package com.thealgorithms.strings;

public final class Manacher {

    private Manacher() {
    }

    public static String longestPalindrome(String original) {
        final String transformed = transformForManacher(original);
        int[] palindromeRadii = new int[transformed.length()];
        int currentCenter = 0;
        int currentRightBoundary = 0;
        int longestRadius = 0;
        int centerOfLongest = 0;

        for (int currentIndex = 1; currentIndex < transformed.length() - 1; currentIndex++) {
            int mirrorIndex = 2 * currentCenter - currentIndex;

            if (currentIndex < currentRightBoundary) {
                palindromeRadii[currentIndex] =
                    Math.min(currentRightBoundary - currentIndex, palindromeRadii[mirrorIndex]);
            }

            while (transformed.charAt(currentIndex + 1 + palindromeRadii[currentIndex])
                    == transformed.charAt(currentIndex - 1 - palindromeRadii[currentIndex])) {
                palindromeRadii[currentIndex]++;
            }

            if (currentIndex + palindromeRadii[currentIndex] > currentRightBoundary) {
                currentCenter = currentIndex;
                currentRightBoundary = currentIndex + palindromeRadii[currentIndex];
            }

            if (palindromeRadii[currentIndex] > longestRadius) {
                longestRadius = palindromeRadii[currentIndex];
                centerOfLongest = currentIndex;
            }
        }

        final int startIndexInOriginal = (centerOfLongest - longestRadius) / 2;
        return original.substring(startIndexInOriginal, startIndexInOriginal + longestRadius);
    }

    private static String transformForManacher(String input) {
        if (input.isEmpty()) {
            return "^$";
        }
        StringBuilder transformedBuilder = new StringBuilder("^");
        for (char character : input.toCharArray()) {
            transformedBuilder.append('#').append(character);
        }
        transformedBuilder.append("#$");
        return transformedBuilder.toString();
    }
}