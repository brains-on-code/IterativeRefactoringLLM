package com.thealgorithms.greedyalgorithms;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public final class GaleShapley {

    private GaleShapley() {
        // Utility class; prevent instantiation
    }

    public static Map<String, String> stableMatch(
            Map<String, LinkedList<String>> womenPreferences,
            Map<String, LinkedList<String>> menPreferences
    ) {
        Map<String, String> engagements = new HashMap<>();
        LinkedList<String> freeMen = new LinkedList<>(menPreferences.keySet());

        while (!freeMen.isEmpty()) {
            String man = freeMen.poll();
            LinkedList<String> manPreferences = menPreferences.get(man);

            if (manPreferences == null || manPreferences.isEmpty()) {
                continue;
            }

            String woman = manPreferences.poll();
            String currentFiance = engagements.get(woman);

            if (currentFiance == null) {
                engagements.put(woman, man);
                continue;
            }

            LinkedList<String> womanPreferences = womenPreferences.get(woman);
            if (womanPreferences == null) {
                continue;
            }

            int newManRank = womanPreferences.indexOf(man);
            int currentFianceRank = womanPreferences.indexOf(currentFiance);

            if (newManRank != -1 && (currentFianceRank == -1 || newManRank < currentFianceRank)) {
                engagements.put(woman, man);
                freeMen.add(currentFiance);
            } else {
                freeMen.add(man);
            }
        }

        return engagements;
    }
}