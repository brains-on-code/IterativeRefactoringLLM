package com.thealgorithms.greedyalgorithms;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public final class GaleShapley {

    private GaleShapley() {
    }

    public static Map<String, String> stableMatch(
            Map<String, LinkedList<String>> womenPreferences,
            Map<String, LinkedList<String>> menPreferences) {

        Map<String, String> womanToPartner = new HashMap<>();
        LinkedList<String> unmatchedMen = new LinkedList<>(menPreferences.keySet());

        while (!unmatchedMen.isEmpty()) {
            String man = unmatchedMen.poll();
            LinkedList<String> manPreferences = menPreferences.get(man);

            if (manPreferences == null || manPreferences.isEmpty()) {
                continue;
            }

            String preferredWoman = manPreferences.poll();
            String currentPartner = womanToPartner.get(preferredWoman);

            if (currentPartner == null) {
                womanToPartner.put(preferredWoman, man);
            } else {
                LinkedList<String> womanPreferences = womenPreferences.get(preferredWoman);

                if (womanPreferences == null) {
                    continue;
                }

                boolean prefersNewMan =
                        womanPreferences.indexOf(man) < womanPreferences.indexOf(currentPartner);

                if (prefersNewMan) {
                    womanToPartner.put(preferredWoman, man);
                    unmatchedMen.add(currentPartner);
                } else {
                    unmatchedMen.add(man);
                }
            }
        }
        return womanToPartner;
    }
}