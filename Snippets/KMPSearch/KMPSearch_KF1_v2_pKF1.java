package com.thealgorithms.searches;

class KnuthMorrisPrattSearch {

    int search(String pattern, String text) {
        int patternLength = pattern.length();
        int textLength = text.length();

        int[] lps = new int[patternLength];
        int patternPos = 0;

        computeLongestPrefixSuffixArray(pattern, patternLength, lps);

        int textPos = 0;
        while ((textLength - textPos) >= (patternLength - patternPos)) {
            if (pattern.charAt(patternPos) == text.charAt(textPos)) {
                patternPos++;
                textPos++;
            }
            if (patternPos == patternLength) {
                System.out.println("Found pattern at index " + (textPos - patternPos));
                int foundIndex = textPos - patternPos;
                patternPos = lps[patternPos - 1];
                return foundIndex;
            } else if (textPos < textLength && pattern.charAt(patternPos) != text.charAt(textPos)) {
                if (patternPos != 0) {
                    patternPos = lps[patternPos - 1];
                } else {
                    textPos++;
                }
            }
        }
        System.out.println("No pattern found");
        return -1;
    }

    void computeLongestPrefixSuffixArray(String pattern, int patternLength, int[] lps) {
        int currentLpsLength = 0;
        int currentIndex = 1;
        lps[0] = 0;

        while (currentIndex < patternLength) {
            if (pattern.charAt(currentIndex) == pattern.charAt(currentLpsLength)) {
                currentLpsLength++;
                lps[currentIndex] = currentLpsLength;
                currentIndex++;
            } else {
                if (currentLpsLength != 0) {
                    currentLpsLength = lps[currentLpsLength - 1];
                } else {
                    lps[currentIndex] = 0;
                    currentIndex++;
                }
            }
        }
    }
}