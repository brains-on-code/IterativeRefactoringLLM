package com.thealgorithms.searches;

class KnuthMorrisPrattSearch {

    int search(String pattern, String text) {
        int patternLength = pattern.length();
        int textLength = text.length();

        int[] lps = new int[patternLength];
        int patternPos = 0;

        computeLpsArray(pattern, patternLength, lps);

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

    void computeLpsArray(String pattern, int patternLength, int[] lps) {
        int lengthOfCurrentLps = 0;
        int index = 1;

        lps[0] = 0;

        while (index < patternLength) {
            if (pattern.charAt(index) == pattern.charAt(lengthOfCurrentLps)) {
                lengthOfCurrentLps++;
                lps[index] = lengthOfCurrentLps;
                index++;
            } else {
                if (lengthOfCurrentLps != 0) {
                    lengthOfCurrentLps = lps[lengthOfCurrentLps - 1];
                } else {
                    lps[index] = 0;
                    index++;
                }
            }
        }
    }
}