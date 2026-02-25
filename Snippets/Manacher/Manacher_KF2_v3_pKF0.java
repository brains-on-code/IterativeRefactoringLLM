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
        final int n = processed.length();
        int[] radii = new int[n];

        int center = 0;
        int right = 0;
        int bestRadius = 0;
        int bestCenter = 0;

        for (int i = 1; i < n - 1; i++) {
            int mirror = 2 * center - i;

            if (i < right) {
                radii[i] = Math.min(right - i, radii[mirror]);
            }

            while (processed.charAt(i + 1 + radii[i]) == processed.charAt(i - 1 - radii[i])) {
                radii[i]++;
            }

            int currentRight = i + radii[i];
            if (currentRight > right) {
                center = i;
                right = currentRight;
            }

            if (radii[i] > bestRadius) {
                bestRadius = radii[i];
                bestCenter = i;
            }
        }

        int startIndex = (bestCenter - bestRadius) / 2;
        return input.substring(startIndex, startIndex + bestRadius);
    }

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