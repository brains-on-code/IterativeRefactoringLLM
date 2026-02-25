package com.thealgorithms.searches;

class KMPSearch {

    int search(String pattern, String text) {
        int patternLength = pattern.length();
        int textLength = text.length();

        int[] lps = new int[patternLength];
        int patternPos = 0;

        computeLPSArray(pattern, patternLength, lps);

        int textPos = 0;
        while ((textLength - textPos) >= (patternLength - patternPos)) {
            if (pattern.charAt(patternPos) == text.charAt(textPos)) {
                patternPos++;
                textPos++;
            }

            if (patternPos == patternLength) {
                int matchIndex = textPos - patternPos;
                System.out.println("Found pattern at index " + matchIndex);
                patternPos = lps[patternPos - 1];
                return matchIndex;
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

    void computeLPSArray(String pattern, int patternLength, int[] lps) {
        int lengthOfCurrentLPS = 0;
        int currentIndex = 1;

        lps[0] = 0;

        while (currentIndex < patternLength) {
            if (pattern.charAt(currentIndex) == pattern.charAt(lengthOfCurrentLPS)) {
                lengthOfCurrentLPS++;
                lps[currentIndex] = lengthOfCurrentLPS;
                currentIndex++;
            } else {
                if (lengthOfCurrentLPS != 0) {
                    lengthOfCurrentLPS = lps[lengthOfCurrentLPS - 1];
                } else {
                    lps[currentIndex] = 0;
                    currentIndex++;
                }
            }
        }
    }
}