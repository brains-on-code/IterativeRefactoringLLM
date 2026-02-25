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
        // Prevent instantiation
    }

    /**
     * Finds a stable matching between men and women using the Gale-Shapley algorithm.
     *
     * @param womenPrefs map of each woman to her ordered list of preferred men
     * @param menPrefs   map of each man to his ordered list of preferred women
     * @return map of each woman to the man she is matched with
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
            String currentPartner = engagements.get(woman);

            if (currentPartner == null) {
                engagements.put(woman, man);
                continue;
            }

            LinkedList<String> womanPreferences = womenPrefs.get(woman);
            if (womanPreferences == null) {
                continue;
            }

            boolean prefersNewMan =
                    womanPreferences.indexOf(man) < womanPreferences.indexOf(currentPartner);

            if (prefersNewMan) {
                engagements.put(woman, man);
                freeMen.add(currentPartner);
            } else {
                freeMen.add(man);
            }
        }

        return engagements;
    }
}