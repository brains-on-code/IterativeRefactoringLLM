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

        for (int prefixLength = 0; prefixLength <= targetLength; prefixLength++) {
            List<List<String>> constructionsForPrefix = constructionsAtIndex.get(prefixLength);
            if (constructionsForPrefix.isEmpty()) {
                continue;
            }

            for (String word : wordBank) {
                int wordLength = word.length();
                int nextIndex = prefixLength + wordLength;

                if (nextIndex <= targetLength
                        && target.substring(prefixLength, nextIndex).equals(word)) {

                    List<List<String>> extendedConstructions = new ArrayList<>();

                    for (List<String> construction : constructionsForPrefix) {
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