package com.thealgorithms.dynamicprogramming;

import java.util.ArrayList;
import java.util.List;


public final class AllConstruct {
    private AllConstruct() {
    }


    public static List<List<String>> allConstruct(String target, Iterable<String> wordBank) {
        List<List<List<String>>> table = new ArrayList<>(target.length() + 1);

        for (int i = 0; i <= target.length(); i++) {
            table.add(new ArrayList<>());
        }

        table.get(0).add(new ArrayList<>());

        for (int i = 0; i <= target.length(); i++) {
            if (!table.get(i).isEmpty()) {
                for (String word : wordBank) {
                    if (i + word.length() <= target.length() && target.substring(i, i + word.length()).equals(word)) {

                        List<List<String>> newCombinations = new ArrayList<>();
                        for (List<String> combination : table.get(i)) {
                            List<String> newCombination = new ArrayList<>(combination);
                            newCombination.add(word);
                            newCombinations.add(newCombination);
                        }

                        table.get(i + word.length()).addAll(newCombinations);
                    }
                }
            }
        }

        return table.get(target.length());
    }
}
