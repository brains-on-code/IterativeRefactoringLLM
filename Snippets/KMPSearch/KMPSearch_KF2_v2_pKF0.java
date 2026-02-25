package com.thealgorithms.searches;

class KMPSearch {

    int kmpSearch(String pattern, String text) {
        int patternLength = pattern.length();
        int textLength = text.length();

        if (isInvalidInput(patternLength, textLength)) {
            System.out.println("No pattern found");
            return -1;
        }

        int[] longestPrefixSuffix = buildLongestPrefixSuffixArray(pattern);

        int patternIndex = 0;
        int textIndex = 0;

        while (textLength - textIndex >= patternLength - patternIndex) {
            if (pattern.charAt(patternIndex) == text.charAt(textIndex)) {
                patternIndex++;
                textIndex++;
            }

            if (patternIndex == patternLength) {
                int foundIndex = textIndex - patternIndex;
                System.out.println("Found pattern at index " + foundIndex);
                return foundIndex;
            }

            if (textIndex < textLength && pattern.charAt(patternIndex) != text.charAt(textIndex)) {
                if (patternIndex != 0) {
                    patternIndex = longestPrefixSuffix[patternIndex - 1];
                } else {
                    textIndex++;
                }
            }
        }

        System.out.println("No pattern found");
        return -1;
    }

    private boolean isInvalidInput(int patternLength, int textLength) {
        return patternLength == 0 || textLength == 0 || patternLength > textLength;
    }

    private int[] buildLongestPrefixSuffixArray(String pattern) {
        int patternLength = pattern.length();
        int[] lps = new int[patternLength];

        int lengthOfPreviousLPS = 0;
        int currentIndex = 1;
        lps[0] = 0;

        while (currentIndex < patternLength) {
            if (pattern.charAt(currentIndex) == pattern.charAt(lengthOfPreviousLPS)) {
                lengthOfPreviousLPS++;
                lps[currentIndex] = lengthOfPreviousLPS;
                currentIndex++;
            } else {
                if (lengthOfPreviousLPS != 0) {
                    lengthOfPreviousLPS = lps[lengthOfPreviousLPS - 1];
                } else {
                    lps[currentIndex] = 0;
                    currentIndex++;
                }
            }
        }

        return lps;
    }
}