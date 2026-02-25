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
     * Function to find stable matches between men and women.
     *
     * @param womenPreferences A map containing women's preferences where each key is a woman and the value is an array of men in order of preference.
     * @param menPreferences   A map containing men's preferences where each key is a man and the value is an array of women in order of preference.
     * @return A map containing stable matches where the key is a woman and the value is her matched man.
     */
    public static Map<String, String> stableMatch(
            Map<String, LinkedList<String>> womenPreferences,
            Map<String, LinkedList<String>> menPreferences
    ) {
        Map<String, String> engagementsByWoman = new HashMap<>();
        LinkedList<String> freeMenQueue = new LinkedList<>(menPreferences.keySet());

        while (!freeMenQueue.isEmpty()) {
            String currentMan = freeMenQueue.poll();
            LinkedList<String> currentManPreferences = menPreferences.get(currentMan);

            if (currentManPreferences == null || currentManPreferences.isEmpty()) {
                continue;
            }

            String preferredWoman = currentManPreferences.poll();
            String currentFiance = engagementsByWoman.get(preferredWoman);

            if (currentFiance == null) {
                engagementsByWoman.put(preferredWoman, currentMan);
            } else {
                LinkedList<String> preferredWomanPreferences = womenPreferences.get(preferredWoman);

                if (preferredWomanPreferences == null) {
                    continue;
                }

                boolean prefersNewMan =
                        preferredWomanPreferences.indexOf(currentMan)
                                < preferredWomanPreferences.indexOf(currentFiance);

                if (prefersNewMan) {
                    engagementsByWoman.put(preferredWoman, currentMan);
                    freeMenQueue.add(currentFiance);
                } else {
                    freeMenQueue.add(currentMan);
                }
            }
        }

        return engagementsByWoman;
    }
}