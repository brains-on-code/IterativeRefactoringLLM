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
        List<List<List<String>>> table = initializeTable(targetLength);

        // Base case: one way to construct the empty string
        table.get(0).add(new ArrayList<>());

        for (int i = 0; i <= targetLength; i++) {
            List<List<String>> currentCombinations = table.get(i);
            if (currentCombinations.isEmpty()) {
                continue;
            }

            for (String word : wordBank) {
                int nextIndex = i + word.length();
                if (nextIndex > targetLength || !target.startsWith(word, i)) {
                    continue;
                }

                List<List<String>> extendedCombinations = extendCombinations(currentCombinations, word);
                table.get(nextIndex).addAll(extendedCombinations);
            }
        }

        return table.get(targetLength);
    }

    private static List<List<List<String>>> initializeTable(int targetLength) {
        List<List<List<String>>> table = new ArrayList<>(targetLength + 1);
        for (int i = 0; i <= targetLength; i++) {
            table.add(new ArrayList<>());
        }
        return table;
    }

    private static List<List<String>> extendCombinations(List<List<String>> combinations, String word) {
        List<List<String>> result = new ArrayList<>(combinations.size());
        for (List<String> combination : combinations) {
            List<String> extended = new ArrayList<>(combination.size() + 1);
            extended.addAll(combination);
            extended.add(word);
            result.add(extended);
        }
        return result;
    }
}