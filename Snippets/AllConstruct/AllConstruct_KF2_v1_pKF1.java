package com.thealgorithms.dynamicprogramming;

import java.util.ArrayList;
import java.util.List;

public final class AllConstruct {
    private AllConstruct() {
    }

    public static List<List<String>> allConstruct(String target, Iterable<String> wordBank) {
        int targetLength = target.length();
        List<List<List<String>>> constructionsAtIndex = new ArrayList<>(targetLength + 1);

        for (int index = 0; index <= targetLength; index++) {
            constructionsAtIndex.add(new ArrayList<>());
        }

        constructionsAtIndex.get(0).add(new ArrayList<>());

        for (int index = 0; index <= targetLength; index++) {
            List<List<String>> currentConstructions = constructionsAtIndex.get(index);
            if (currentConstructions.isEmpty()) {
                continue;
            }

            for (String word : wordBank) {
                int wordLength = word.length();
                int nextIndex = index + wordLength;

                if (nextIndex <= targetLength && target.substring(index, nextIndex).equals(word)) {
                    List<List<String>> extendedConstructions = new ArrayList<>();

                    for (List<String> construction : currentConstructions) {
                        List<String> newConstruction = new ArrayList<>(construction);
                        newConstruction.add(word);
                        extendedConstructions.add(newConstruction);
                    }

                    constructionsAtIndex.get(nextIndex).addAll(extendedConstructions);
                }
            }
        }

        return constructionsAtIndex.get(targetLength);
    }
}