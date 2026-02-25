package com.thealgorithms.greedyalgorithms;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Gale–Shapley algorithm for the Stable Marriage Problem.
 * See: https://en.wikipedia.org/wiki/Stable_marriage_problem
 */
public final class GaleShapley {

    private GaleShapley() {
        // Utility class; prevent instantiation
    }

    /**
     * Computes a stable matching between men and women using the Gale–Shapley algorithm.
     *
     * @param womenPrefs mapping from each woman to her ordered list of preferred men
     * @param menPrefs   mapping from each man to his ordered list of preferred women
     * @return mapping from each woman to the man she is matched with
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
                // Woman is currently unmatched; engage her to this man
                engagements.put(woman, man);
                continue;
            }

            LinkedList<String> womanPreferences = womenPrefs.get(woman);
            if (womanPreferences == null) {
                continue;
            }

            boolean womanPrefersNewMan =
                    womanPreferences.indexOf(man) < womanPreferences.indexOf(currentPartner);

            if (womanPrefersNewMan) {
                // Woman prefers the new man; update engagement and free the previous partner
                engagements.put(woman, man);
                freeMen.add(currentPartner);
            } else {
                // Woman prefers her current partner; this man remains free
                freeMen.add(man);
            }
        }

        return engagements;
    }
}