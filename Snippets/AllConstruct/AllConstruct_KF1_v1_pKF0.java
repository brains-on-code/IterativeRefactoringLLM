package com.thealgorithms.dynamicprogramming;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for dynamic programming string segmentation.
 */
public final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Returns all possible segmentations of the input string using the given dictionary.
     *
     * @param input      the string to segment
     * @param dictionary iterable collection of allowed words
     * @return list of segmentations, where each segmentation is a list of words
     */
    public static List<List<String>> method1(String input, Iterable<String> dictionary) {
        int n = input.length();
        List<List<List<String>>> segmentations = new ArrayList<>(n + 1);

        // Initialize DP table: segmentations[i] holds all segmentations of input[0..i)
        for (int i = 0; i <= n; i++) {
            segmentations.add(new ArrayList<>());
        }

        // Base case: empty prefix has one empty segmentation
        segmentations.get(0).add(new ArrayList<>());

        for (int i = 0; i <= n; i++) {
            if (segmentations.get(i).isEmpty()) {
                continue;
            }

            for (String word : dictionary) {
                int wordLength = word.length();
                int endIndex = i + wordLength;

                if (endIndex > n) {
                    continue;
                }

                if (!input.regionMatches(i, word, 0, wordLength)) {
                    continue;
                }

                List<List<String>> newSegmentations = new ArrayList<>();
                for (List<String> existingSegmentation : segmentations.get(i)) {
                    List<String> extendedSegmentation = new ArrayList<>(existingSegmentation);
                    extendedSegmentation.add(word);
                    newSegmentations.add(extendedSegmentation);
                }

                segmentations.get(endIndex).addAll(newSegmentations);
            }
        }

        return segmentations.get(n);
    }
}