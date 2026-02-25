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
        int length = input.length();
        List<List<List<String>>> segmentations = initializeSegmentations(length);

        // Base case: empty prefix has one empty segmentation
        segmentations.get(0).add(new ArrayList<>());

        for (int startIndex = 0; startIndex <= length; startIndex++) {
            List<List<String>> currentSegmentations = segmentations.get(startIndex);
            if (currentSegmentations.isEmpty()) {
                continue;
            }

            for (String word : dictionary) {
                int endIndex = startIndex + word.length();
                if (!canPlaceWord(input, word, startIndex, endIndex)) {
                    continue;
                }

                extendSegmentations(segmentations, currentSegmentations, word, endIndex);
            }
        }

        return segmentations.get(length);
    }

    private static List<List<List<String>>> initializeSegmentations(int length) {
        List<List<List<String>>> segmentations = new ArrayList<>(length + 1);
        for (int i = 0; i <= length; i++) {
            segmentations.add(new ArrayList<>());
        }
        return segmentations;
    }

    private static boolean canPlaceWord(String input, String word, int startIndex, int endIndex) {
        if (endIndex > input.length()) {
            return false;
        }
        return input.regionMatches(startIndex, word, 0, word.length());
    }

    private static void extendSegmentations(
        List<List<List<String>>> segmentations,
        List<List<String>> currentSegmentations,
        String word,
        int endIndex
    ) {
        List<List<String>> extendedSegmentations = new ArrayList<>();
        for (List<String> existingSegmentation : currentSegmentations) {
            List<String> newSegmentation = new ArrayList<>(existingSegmentation);
            newSegmentation.add(word);
            extendedSegmentations.add(newSegmentation);
        }
        segmentations.get(endIndex).addAll(extendedSegmentations);
    }
}