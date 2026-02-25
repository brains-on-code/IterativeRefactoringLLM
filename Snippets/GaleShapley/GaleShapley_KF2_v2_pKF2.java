package com.thealgorithms.greedyalgorithms;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Implementation of the Gale–Shapley stable matching algorithm.
 *
 * <p>Given preference lists for two equally sized sets (men and women),
 * this method produces a stable matching where no unmatched pair would
 * both prefer each other over their current partners.
 */
public final class GaleShapley {

    private GaleShapley() {
        // Prevent instantiation
    }

    /**
     * Computes a stable matching using the Gale–Shapley algorithm.
     *
     * @param womenPreferences mapping from each woman to her ordered list of preferred men
     * @param menPreferences   mapping from each man to his ordered list of preferred women
     * @return a map from each woman to the man she is matched with
     */
    public static Map<String, String> stableMatch(
        Map<String, LinkedList<String>> womenPreferences,
        Map<String, LinkedList<String>> menPreferences
    ) {
        Map<String, String> engagements = new HashMap<>();
        LinkedList<String> freeMen = new LinkedList<>(menPreferences.keySet());

        while (!freeMen.isEmpty()) {
            String man = freeMen.poll();
            LinkedList<String> manPreferenceList = menPreferences.get(man);

            if (manPreferenceList == null || manPreferenceList.isEmpty()) {
                continue;
            }

            String woman = manPreferenceList.poll();
            String currentFiance = engagements.get(woman);

            if (currentFiance == null) {
                engagements.put(woman, man);
                continue;
            }

            LinkedList<String> womanPreferenceList = womenPreferences.get(woman);
            if (womanPreferenceList == null) {
                continue;
            }

            boolean prefersNewMan =
                womanPreferenceList.indexOf(man) < womanPreferenceList.indexOf(currentFiance);

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