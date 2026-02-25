package com.thealgorithms.strings;

public final class KMP {

    private KMP() {
    }

    public static void main(String[] args) {
        final String text = "AAAAABAAABA";
        final String pattern = "AAAA";
        searchPattern(text, pattern);
    }

    public static void searchPattern(final String text, final String pattern) {
        final int textLength = text.length();
        final int patternLength = pattern.length();
        final int[] lpsArray = buildLpsArray(pattern);
        int patternPosition = 0;

        for (int textPosition = 0; textPosition < textLength; textPosition++) {
            while (patternPosition > 0 && text.charAt(textPosition) != pattern.charAt(patternPosition)) {
                patternPosition = lpsArray[patternPosition - 1];
            }

            if (text.charAt(textPosition) == pattern.charAt(patternPosition)) {
                patternPosition++;
            }

            if (patternPosition == patternLength) {
                System.out.println("Pattern starts at index: " + (textPosition + 1 - patternLength));
                patternPosition = lpsArray[patternPosition - 1];
            }
        }
    }

    private static int[] buildLpsArray(final String pattern) {
        final int patternLength = pattern.length();
        final int[] lpsArray = new int[patternLength];
        lpsArray[0] = 0;
        int currentLpsLength = 0;

        for (int patternIndex = 1; patternIndex < patternLength; patternIndex++) {
            while (currentLpsLength > 0 && pattern.charAt(currentLpsLength) != pattern.charAt(patternIndex)) {
                currentLpsLength = lpsArray[currentLpsLength - 1];
            }

            if (pattern.charAt(currentLpsLength) == pattern.charAt(patternIndex)) {
                currentLpsLength++;
            }

            lpsArray[patternIndex] = currentLpsLength;
        }

        return lpsArray;
    }
}