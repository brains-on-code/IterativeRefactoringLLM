package com.thealgorithms.searches;

class KMPSearch {

    int kmpSearch(String pattern, String text) {
        int patternLength = pattern.length();
        int textLength = text.length();

        int[] lps = new int[patternLength];
        int patternIndex = 0;

        computeLPSArray(pattern, patternLength, lps);

        int textIndex = 0;
        while ((textLength - textIndex) >= (patternLength - patternIndex)) {
            if (pattern.charAt(patternIndex) == text.charAt(textIndex)) {
                patternIndex++;
                textIndex++;
            }

            if (patternIndex == patternLength) {
                int foundIndex = textIndex - patternIndex;
                System.out.println("Found pattern at index " + foundIndex);
                patternIndex = lps[patternIndex - 1];
                return foundIndex;
            } else if (textIndex < textLength && pattern.charAt(patternIndex) != text.charAt(textIndex)) {
                if (patternIndex != 0) {
                    patternIndex = lps[patternIndex - 1];
                } else {
                    textIndex++;
                }
            }
        }

        System.out.println("No pattern found");
        return -1;
    }

    void computeLPSArray(String pattern, int patternLength, int[] lps) {
        int lengthOfPreviousLPS = 0;
        int index = 1;

        lps[0] = 0;

        while (index < patternLength) {
            if (pattern.charAt(index) == pattern.charAt(lengthOfPreviousLPS)) {
                lengthOfPreviousLPS++;
                lps[index] = lengthOfPreviousLPS;
                index++;
            } else {
                if (lengthOfPreviousLPS != 0) {
                    lengthOfPreviousLPS = lps[lengthOfPreviousLPS - 1];
                } else {
                    lps[index] = 0;
                    index++;
                }
            }
        }
    }
}