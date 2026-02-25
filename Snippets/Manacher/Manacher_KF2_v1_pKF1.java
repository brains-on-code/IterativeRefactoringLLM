package com.thealgorithms.strings;

public final class Manacher {

    private Manacher() {
    }

    public static String longestPalindrome(String input) {
        final String transformed = transformForManacher(input);
        int[] palindromeRadii = new int[transformed.length()];
        int currentCenter = 0;
        int currentRightBoundary = 0;
        int longestRadius = 0;
        int longestCenterIndex = 0;

        for (int i = 1; i < transformed.length() - 1; i++) {
            int mirrorIndex = 2 * currentCenter - i;

            if (i < currentRightBoundary) {
                palindromeRadii[i] =
                    Math.min(currentRightBoundary - i, palindromeRadii[mirrorIndex]);
            }

            while (transformed.charAt(i + 1 + palindromeRadii[i])
                    == transformed.charAt(i - 1 - palindromeRadii[i])) {
                palindromeRadii[i]++;
            }

            if (i + palindromeRadii[i] > currentRightBoundary) {
                currentCenter = i;
                currentRightBoundary = i + palindromeRadii[i];
            }

            if (palindromeRadii[i] > longestRadius) {
                longestRadius = palindromeRadii[i];
                longestCenterIndex = i;
            }
        }

        final int startIndex = (longestCenterIndex - longestRadius) / 2;
        return input.substring(startIndex, startIndex + longestRadius);
    }

    private static String transformForManacher(String input) {
        if (input.isEmpty()) {
            return "^$";
        }
        StringBuilder transformed = new StringBuilder("^");
        for (char character : input.toCharArray()) {
            transformed.append('#').append(character);
        }
        transformed.append("#$");
        return transformed.toString();
    }
}