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
        List<List<List<String>>> constructionsByPosition = new ArrayList<>(targetLength + 1);

        for (int position = 0; position <= targetLength; position++) {
            constructionsByPosition.add(new ArrayList<>());
        }

        // Base case: one way to construct the empty prefix (use no words)
        constructionsByPosition.get(0).add(new ArrayList<>());

        for (int position = 0; position <= targetLength; position++) {
            List<List<String>> constructionsAtPosition = constructionsByPosition.get(position);
            if (constructionsAtPosition.isEmpty()) {
                continue;
            }

            for (String word : wordBank) {
                int nextPosition = position + word.length();
                if (nextPosition <= targetLength
                    && target.substring(position, nextPosition).equals(word)) {

                    List<List<String>> updatedConstructions = new ArrayList<>();
                    for (List<String> construction : constructionsAtPosition) {
                        List<String> extendedConstruction = new ArrayList<>(construction);
                        extendedConstruction.add(word);
                        updatedConstructions.add(extendedConstruction);
                    }

                    constructionsByPosition.get(nextPosition).addAll(updatedConstructions);
                }
            }
        }

        return constructionsByPosition.get(targetLength);
    }
}