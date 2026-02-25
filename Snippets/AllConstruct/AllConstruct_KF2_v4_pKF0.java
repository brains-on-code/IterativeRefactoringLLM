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
        List<List<List<String>>> constructionsTable = initializeConstructionsTable(targetLength);

        // Base case: one way to construct the empty string
        constructionsTable.get(0).add(new ArrayList<>());

        for (int position = 0; position <= targetLength; position++) {
            List<List<String>> currentConstructions = constructionsTable.get(position);
            if (currentConstructions.isEmpty()) {
                continue;
            }

            for (String word : wordBank) {
                int nextPosition = position + word.length();
                if (nextPosition > targetLength || !target.startsWith(word, position)) {
                    continue;
                }

                List<List<String>> newConstructions =
                    extendConstructionsWithWord(currentConstructions, word);
                constructionsTable.get(nextPosition).addAll(newConstructions);
            }
        }

        return constructionsTable.get(targetLength);
    }

    private static List<List<List<String>>> initializeConstructionsTable(int targetLength) {
        List<List<List<String>>> table = new ArrayList<>(targetLength + 1);
        for (int i = 0; i <= targetLength; i++) {
            table.add(new ArrayList<>());
        }
        return table;
    }

    private static List<List<String>> extendConstructionsWithWord(
        List<List<String>> existingConstructions,
        String word
    ) {
        List<List<String>> extendedConstructions = new ArrayList<>(existingConstructions.size());
        for (List<String> construction : existingConstructions) {
            List<String> newConstruction = new ArrayList<>(construction.size() + 1);
            newConstruction.addAll(construction);
            newConstruction.add(word);
            extendedConstructions.add(newConstruction);
        }
        return extendedConstructions;
    }
}