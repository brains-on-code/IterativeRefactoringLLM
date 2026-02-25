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
        LinkedList<String> freeMen = new LinkedList<>(menPreferences.keySet());

        while (!freeMen.isEmpty()) {
            String man = freeMen.poll();
            LinkedList<String> manPreferenceList = menPreferences.get(man);

            if (manPreferenceList == null || manPreferenceList.isEmpty()) {
                continue;
            }

            String woman = manPreferenceList.poll();
            String currentPartner = womanToManMatch.get(woman);

            if (currentPartner == null) {
                womanToManMatch.put(woman, man);
            } else {
                LinkedList<String> womanPreferenceList = womenPreferences.get(woman);

                if (womanPreferenceList == null) {
                    continue;
                }

                boolean prefersNewMan =
                        womanPreferenceList.indexOf(man)
                                < womanPreferenceList.indexOf(currentPartner);

                if (prefersNewMan) {
                    womanToManMatch.put(woman, man);
                    freeMen.add(currentPartner);
                } else {
                    freeMen.add(man);
                }
            }
        }

        return womanToManMatch;
    }
}