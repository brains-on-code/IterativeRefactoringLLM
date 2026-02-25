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
            LinkedList<String> manPref = menPrefs.get(man);

            if (manPref == null || manPref.isEmpty()) {
                continue;
            }

            String woman = manPref.poll();
            String currentPartner = engagements.get(woman);

            if (currentPartner == null) {
                engagements.put(woman, man);
                continue;
            }

            LinkedList<String> womanPrefList = womenPrefs.get(woman);
            if (womanPrefList == null) {
                continue;
            }

            boolean prefersNewMan =
                    womanPrefList.indexOf(man) < womanPrefList.indexOf(currentPartner);

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