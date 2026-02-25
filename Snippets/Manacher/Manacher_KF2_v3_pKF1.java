package com.thealgorithms.strings;

public final class Manacher {

    private Manacher() {
    }

    public static String longestPalindrome(String input) {
        final String transformed = transformInput(input);
        int[] palindromeRadii = new int[transformed.length()];
        int currentCenter = 0;
        int currentRightBoundary = 0;
        int longestRadius = 0;
        int centerOfLongest = 0;

        for (int position = 1; position < transformed.length() - 1; position++) {
            int mirrorPosition = 2 * currentCenter - position;

            if (position < currentRightBoundary) {
                palindromeRadii[position] =
                    Math.min(currentRightBoundary - position, palindromeRadii[mirrorPosition]);
            }

            while (transformed.charAt(position + 1 + palindromeRadii[position])
                    == transformed.charAt(position - 1 - palindromeRadii[position])) {
                palindromeRadii[position]++;
            }

            if (position + palindromeRadii[position] > currentRightBoundary) {
                currentCenter = position;
                currentRightBoundary = position + palindromeRadii[position];
            }

            if (palindromeRadii[position] > longestRadius) {
                longestRadius = palindromeRadii[position];
                centerOfLongest = position;
            }
        }

        final int startIndex = (centerOfLongest - longestRadius) / 2;
        return input.substring(startIndex, startIndex + longestRadius);
    }

    private static String transformInput(String input) {
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