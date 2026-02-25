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
        // Utility class; prevent instantiation
    }

    /**
     * Finds stable matches between men and women using the Gale-Shapley algorithm.
     *
     * @param womenPrefs a map of women's preferences; each key is a woman and the value is a list of men in order of preference
     * @param menPrefs   a map of men's preferences; each key is a man and the value is a list of women in order of preference
     * @return a map of stable matches where the key is a woman and the value is her matched man
     */
    public static Map<String, String> stableMatch(
            Map<String, LinkedList<String>> womenPrefs,
            Map<String, LinkedList<String>> menPrefs
    ) {
        Map<String, String> engagements = new HashMap<>();
        LinkedList<String> freeMen = new LinkedList<>(menPrefs.keySet());

        while (!freeMen.isEmpty()) {
            String man = freeMen.poll();
            LinkedList<String> manPreferences = menPrefs.get(man);

            if (manPreferences == null || manPreferences.isEmpty()) {
                continue;
            }

            String woman = manPreferences.poll();
            String currentFiance = engagements.get(woman);

            if (currentFiance == null) {
                engagements.put(woman, man);
                continue;
            }

            LinkedList<String> womanPreferences = womenPrefs.get(woman);
            if (womanPreferences == null) {
                continue;
            }

            boolean prefersNewMan =
                    womanPreferences.indexOf(man) < womanPreferences.indexOf(currentFiance);

            if (prefersNewMan) {
                engagements.put(woman, man);
                freeMen.add(currentFiance);
            } else {
                freeMen.add(man);
            }
        }

        return engagements;
    }
}