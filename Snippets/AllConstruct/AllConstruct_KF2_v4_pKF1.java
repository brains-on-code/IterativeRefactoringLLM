package com.thealgorithms.dynamicprogramming;

import java.util.ArrayList;
import java.util.List;

public final class AllConstruct {
    private AllConstruct() {
    }

    public static List<List<String>> allConstruct(String target, Iterable<String> wordBank) {
        int targetLength = target.length();
        List<List<List<String>>> constructionsByLength = new ArrayList<>(targetLength + 1);

        for (int length = 0; length <= targetLength; length++) {
            constructionsByLength.add(new ArrayList<>());
        }

        constructionsByLength.get(0).add(new ArrayList<>());

        for (int currentLength = 0; currentLength <= targetLength; currentLength++) {
            List<List<String>> currentConstructions = constructionsByLength.get(currentLength);
            if (currentConstructions.isEmpty()) {
                continue;
            }

            for (String word : wordBank) {
                int wordLength = word.length();
                int newLength = currentLength + wordLength;

                if (newLength <= targetLength
                        && target.substring(currentLength, newLength).equals(word)) {

                    List<List<String>> updatedConstructions = new ArrayList<>();

                    for (List<String> construction : currentConstructions) {
                        List<String> newConstruction = new ArrayList<>(construction);
                        newConstruction.add(word);
                        updatedConstructions.add(newConstruction);
                    }

                    constructionsByLength.get(newLength).addAll(updatedConstructions);
                }
            }
        }

        return constructionsByLength.get(targetLength);
    }
}