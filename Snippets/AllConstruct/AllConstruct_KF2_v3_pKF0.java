package com.thealgorithms.dynamicprogramming;

import java.util.ArrayList;
import java.util.List;

public final class AllConstruct {

    private AllConstruct() {
        // Utility class; prevent instantiation
    }

    /**
     * Returns all possible ways to construct the target string by concatenating
     * words from the given wordBank. Each word can be used multiple times.
     *
     * @param target   the string to construct
     * @param wordBank the collection of words to use
     * @return a list of all constructions, where each construction is a list of words
     */
    public static List<List<String>> allConstruct(String target, Iterable<String> wordBank) {
        int targetLength = target.length();
        List<List<List<String>>> table = createEmptyTable(targetLength);

        // Base case: one way to construct the empty string
        table.get(0).add(new ArrayList<>());

        for (int position = 0; position <= targetLength; position++) {
            List<List<String>> currentCombinations = table.get(position);
            if (currentCombinations.isEmpty()) {
                continue;
            }

            for (String word : wordBank) {
                int nextPosition = position + word.length();
                if (nextPosition > targetLength || !target.startsWith(word, position)) {
                    continue;
                }

                List<List<String>> extendedCombinations = appendWordToCombinations(currentCombinations, word);
                table.get(nextPosition).addAll(extendedCombinations);
            }
        }

        return table.get(targetLength);
    }

    private static List<List<List<String>>> createEmptyTable(int targetLength) {
        List<List<List<String>>> table = new ArrayList<>(targetLength + 1);
        for (int i = 0; i <= targetLength; i++) {
            table.add(new ArrayList<>());
        }
        return table;
    }

    private static List<List<String>> appendWordToCombinations(List<List<String>> combinations, String word) {
        List<List<String>> result = new ArrayList<>(combinations.size());
        for (List<String> combination : combinations) {
            List<String> extendedCombination = new ArrayList<>(combination.size() + 1);
            extendedCombination.addAll(combination);
            extendedCombination.add(word);
            result.add(extendedCombination);
        }
        return result;
    }
}