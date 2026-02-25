package com.thealgorithms.searches;

class KMPSearch {

    int kmpSearch(String pattern, String text) {
        if (pattern == null || text == null) {
            return -1;
        }

        int patternLength = pattern.length();
        int textLength = text.length();

        if (patternLength == 0 || textLength == 0 || patternLength > textLength) {
            System.out.println("No pattern found");
            return -1;
        }

        int[] lps = buildLongestPrefixSuffixArray(pattern);

        int textIndex = 0;
        int patternIndex = 0;

        while (textIndex < textLength) {
            if (pattern.charAt(patternIndex) == text.charAt(textIndex)) {
                textIndex++;
                patternIndex++;

                if (patternIndex == patternLength) {
                    int foundIndex = textIndex - patternIndex;
                    System.out.println("Found pattern at index " + foundIndex);
                    return foundIndex;
                }
            } else {
                if (patternIndex != 0) {
                    patternIndex = lps[patternIndex - 1];
                } else {
                    textIndex++;
                }
            }

            if ((textLength - textIndex) < (patternLength - patternIndex)) {
                break;
            }
        }

        System.out.println("No pattern found");
        return -1;
    }

    private int[] buildLongestPrefixSuffixArray(String pattern) {
        int patternLength = pattern.length();
        int[] lps = new int[patternLength];

        int prefixLength = 0;
        int index = 1;

        lps[0] = 0;

        while (index < patternLength) {
            if (pattern.charAt(index) == pattern.charAt(prefixLength)) {
                prefixLength++;
                lps[index] = prefixLength;
                index++;
            } else if (prefixLength != 0) {
                prefixLength = lps[prefixLength - 1];
            } else {
                lps[index] = 0;
                index++;
            }
        }

        return lps;
    }
}