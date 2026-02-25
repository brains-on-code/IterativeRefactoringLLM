package com.thealgorithms.greedyalgorithms;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Implementation of the Gale-Shapley Algorithm for Stable Matching.
 * Problem link: https://en.wikipedia.org/wiki/Stable_marriage_problem
 */
public final class GaleShapley {

    private GaleShapley() {
    }

    /**
     * Finds stable matches between men and women.
     *
     * @param womenPreferences A map containing women's preferences where each key is a woman and the value is an ordered list of men by preference.
     * @param menPreferences   A map containing men's preferences where each key is a man and the value is an ordered list of women by preference.
     * @return A map containing stable matches where the key is a woman and the value is her matched man.
     */
    public static Map<String, String> stableMatch(
            Map<String, LinkedList<String>> womenPreferences,
            Map<String, LinkedList<String>> menPreferences
    ) {
        Map<String, String> womanToManMatch = new HashMap<>();
        LinkedList<String> freeMenQueue = new LinkedList<>(menPreferences.keySet());

        while (!freeMenQueue.isEmpty()) {
            String man = freeMenQueue.poll();
            LinkedList<String> currentManPreferences = menPreferences.get(man);

            if (currentManPreferences == null || currentManPreferences.isEmpty()) {
                continue;
            }

            String preferredWoman = currentManPreferences.poll();
            String currentMatchedMan = womanToManMatch.get(preferredWoman);

            if (currentMatchedMan == null) {
                womanToManMatch.put(preferredWoman, man);
            } else {
                LinkedList<String> currentWomanPreferences = womenPreferences.get(preferredWoman);

                if (currentWomanPreferences == null) {
                    continue;
                }

                boolean prefersNewMan =
                        currentWomanPreferences.indexOf(man)
                                < currentWomanPreferences.indexOf(currentMatchedMan);

                if (prefersNewMan) {
                    womanToManMatch.put(preferredWoman, man);
                    freeMenQueue.add(currentMatchedMan);
                } else {
                    freeMenQueue.add(man);
                }
            }
        }

        return womanToManMatch;
    }
}