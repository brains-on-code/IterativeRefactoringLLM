package com.thealgorithms.dynamicprogramming;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for constructing all possible combinations of words from a word bank
 * that can form a given target string.
 */
public final class AllConstruct {

    private AllConstruct() {
        // Prevent instantiation
    }

    /**
     * Returns all possible ways to construct the target string by concatenating words
     * from the given word bank. Each word can be used multiple times.
     *
     * @param target   the string to construct
     * @param wordBank an iterable collection of words that can be used to construct the target
     * @return a list of combinations, where each combination is a list of words that
     *         concatenate to form the target
     */
    public static List<List<String>> allConstruct(String target, Iterable<String> wordBank) {
        int targetLength = target.length();
        List<List<List<String>>> constructionsByIndex = new ArrayList<>(targetLength + 1);

        for (int index = 0; index <= targetLength; index++) {
            constructionsByIndex.add(new ArrayList<>());
        }

        // Base case: one way to construct the empty prefix (use no words)
        constructionsByIndex.get(0).add(new ArrayList<>());

        for (int startIndex = 0; startIndex <= targetLength; startIndex++) {
            List<List<String>> constructionsAtStart = constructionsByIndex.get(startIndex);
            if (constructionsAtStart.isEmpty()) {
                continue;
            }

            for (String word : wordBank) {
                int endIndex = startIndex + word.length();
                if (endIndex <= targetLength
                    && target.substring(startIndex, endIndex).equals(word)) {

                    List<List<String>> extendedConstructions = new ArrayList<>();
                    for (List<String> partialConstruction : constructionsAtStart) {
                        List<String> newConstruction = new ArrayList<>(partialConstruction);
                        newConstruction.add(word);
                        extendedConstructions.add(newConstruction);
                    }

                    constructionsByIndex.get(endIndex).addAll(extendedConstructions);
                }
            }
        }

        return constructionsByIndex.get(targetLength);
    }
}