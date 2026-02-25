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
        List<List<List<String>>> segmentationsByIndex = createEmptySegmentations(length);

        // Base case: empty prefix has one empty segmentation
        segmentationsByIndex.get(0).add(new ArrayList<>());

        for (int startIndex = 0; startIndex <= length; startIndex++) {
            List<List<String>> currentSegmentations = segmentationsByIndex.get(startIndex);
            if (currentSegmentations.isEmpty()) {
                continue;
            }

            for (String word : dictionary) {
                int endIndex = startIndex + word.length();
                if (!matchesAtPosition(input, word, startIndex, endIndex)) {
                    continue;
                }
                addWordToSegmentations(segmentationsByIndex, currentSegmentations, word, endIndex);
            }
        }

        return segmentationsByIndex.get(length);
    }

    private static List<List<List<String>>> createEmptySegmentations(int length) {
        List<List<List<String>>> segmentations = new ArrayList<>(length + 1);
        for (int i = 0; i <= length; i++) {
            segmentations.add(new ArrayList<>());
        }
        return segmentations;
    }

    private static boolean matchesAtPosition(String input, String word, int startIndex, int endIndex) {
        if (endIndex > input.length()) {
            return false;
        }
        return input.regionMatches(startIndex, word, 0, word.length());
    }

    private static void addWordToSegmentations(
        List<List<List<String>>> segmentationsByIndex,
        List<List<String>> baseSegmentations,
        String word,
        int endIndex
    ) {
        List<List<String>> extendedSegmentations = new ArrayList<>(baseSegmentations.size());
        for (List<String> baseSegmentation : baseSegmentations) {
            List<String> newSegmentation = new ArrayList<>(baseSegmentation.size() + 1);
            newSegmentation.addAll(baseSegmentation);
            newSegmentation.add(word);
            extendedSegmentations.add(newSegmentation);
        }
        segmentationsByIndex.get(endIndex).addAll(extendedSegmentations);
    }
}