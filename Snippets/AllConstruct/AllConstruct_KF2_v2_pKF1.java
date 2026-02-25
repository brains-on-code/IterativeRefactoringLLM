package com.thealgorithms.dynamicprogramming;

import java.util.ArrayList;
import java.util.List;

public final class AllConstruct {
    private AllConstruct() {
    }

    public static List<List<String>> allConstruct(String target, Iterable<String> wordBank) {
        int targetLength = target.length();
        List<List<List<String>>> constructionsByPosition = new ArrayList<>(targetLength + 1);

        for (int position = 0; position <= targetLength; position++) {
            constructionsByPosition.add(new ArrayList<>());
        }

        constructionsByPosition.get(0).add(new ArrayList<>());

        for (int position = 0; position <= targetLength; position++) {
            List<List<String>> constructionsAtPosition = constructionsByPosition.get(position);
            if (constructionsAtPosition.isEmpty()) {
                continue;
            }

            for (String word : wordBank) {
                int wordLength = word.length();
                int nextPosition = position + wordLength;

                if (nextPosition <= targetLength && target.substring(position, nextPosition).equals(word)) {
                    List<List<String>> updatedConstructions = new ArrayList<>();

                    for (List<String> construction : constructionsAtPosition) {
                        List<String> newConstruction = new ArrayList<>(construction);
                        newConstruction.add(word);
                        updatedConstructions.add(newConstruction);
                    }

                    constructionsByPosition.get(nextPosition).addAll(updatedConstructions);
                }
            }
        }

        return constructionsByPosition.get(targetLength);
    }
}