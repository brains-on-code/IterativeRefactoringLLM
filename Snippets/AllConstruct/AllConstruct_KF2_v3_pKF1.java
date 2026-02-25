package com.thealgorithms.dynamicprogramming;

import java.util.ArrayList;
import java.util.List;

public final class AllConstruct {
    private AllConstruct() {
    }

    public static List<List<String>> allConstruct(String target, Iterable<String> wordBank) {
        int targetLength = target.length();
        List<List<List<String>>> waysToConstructPrefix = new ArrayList<>(targetLength + 1);

        for (int i = 0; i <= targetLength; i++) {
            waysToConstructPrefix.add(new ArrayList<>());
        }

        waysToConstructPrefix.get(0).add(new ArrayList<>());

        for (int prefixEndIndex = 0; prefixEndIndex <= targetLength; prefixEndIndex++) {
            List<List<String>> constructionsForPrefix = waysToConstructPrefix.get(prefixEndIndex);
            if (constructionsForPrefix.isEmpty()) {
                continue;
            }

            for (String word : wordBank) {
                int wordLength = word.length();
                int nextPrefixEndIndex = prefixEndIndex + wordLength;

                if (nextPrefixEndIndex <= targetLength
                        && target.substring(prefixEndIndex, nextPrefixEndIndex).equals(word)) {

                    List<List<String>> extendedConstructions = new ArrayList<>();

                    for (List<String> existingConstruction : constructionsForPrefix) {
                        List<String> newConstruction = new ArrayList<>(existingConstruction);
                        newConstruction.add(word);
                        extendedConstructions.add(newConstruction);
                    }

                    waysToConstructPrefix.get(nextPrefixEndIndex).addAll(extendedConstructions);
                }
            }
        }

        return waysToConstructPrefix.get(targetLength);
    }
}