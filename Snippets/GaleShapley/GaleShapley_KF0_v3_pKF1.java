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
        Map<String, String> womanToPartner = new HashMap<>();
        LinkedList<String> unmatchedMen = new LinkedList<>(menPreferences.keySet());

        while (!unmatchedMen.isEmpty()) {
            String man = unmatchedMen.poll();
            LinkedList<String> manPreferences = menPreferences.get(man);

            if (manPreferences == null || manPreferences.isEmpty()) {
                continue;
            }

            String woman = manPreferences.poll();
            String currentPartner = womanToPartner.get(woman);

            if (currentPartner == null) {
                womanToPartner.put(woman, man);
            } else {
                LinkedList<String> womanPreferences = womenPreferences.get(woman);

                if (womanPreferences == null) {
                    continue;
                }

                boolean prefersNewMan =
                        womanPreferences.indexOf(man)
                                < womanPreferences.indexOf(currentPartner);

                if (prefersNewMan) {
                    womanToPartner.put(woman, man);
                    unmatchedMen.add(currentPartner);
                } else {
                    unmatchedMen.add(man);
                }
            }
        }

        return womanToPartner;
    }
}