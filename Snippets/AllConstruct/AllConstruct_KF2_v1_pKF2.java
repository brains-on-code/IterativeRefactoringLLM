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
     * @param wordBank the collection of words that can be used
     * @return a list of all constructions, where each construction is a list of words
     */
    public static List<List<String>> allConstruct(String target, Iterable<String> wordBank) {
        int targetLength = target.length();

        // table[i] will store all combinations of words that construct target[0..i)
        List<List<List<String>>> table = new ArrayList<>(targetLength + 1);
        for (int i = 0; i <= targetLength; i++) {
            table.add(new ArrayList<>());
        }

        // Base case: there is one way to construct the empty string: use no words
        table.get(0).add(new ArrayList<>());

        for (int i = 0; i <= targetLength; i++) {
            List<List<String>> currentCombinations = table.get(i);
            if (currentCombinations.isEmpty()) {
                continue;
            }

            for (String word : wordBank) {
                int wordLength = word.length();
                int nextIndex = i + wordLength;

                if (nextIndex <= targetLength && target.startsWith(word, i)) {
                    List<List<String>> newCombinations = new ArrayList<>();

                    for (List<String> combination : currentCombinations) {
                        List<String> extendedCombination = new ArrayList<>(combination);
                        extendedCombination.add(word);
                        newCombinations.add(extendedCombination);
                    }

                    table.get(nextIndex).addAll(newCombinations);
                }
            }
        }

        return table.get(targetLength);
    }
}