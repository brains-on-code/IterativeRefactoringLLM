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
        LinkedList<String> freeMen = new LinkedList<>(menPreferences.keySet());

        while (!freeMen.isEmpty()) {
            String man = freeMen.poll();
            LinkedList<String> manPreferenceList = menPreferences.get(man);

            if (manPreferenceList == null || manPreferenceList.isEmpty()) {
                continue;
            }

            String woman = manPreferenceList.poll();
            String currentPartner = womanToPartner.get(woman);

            if (currentPartner == null) {
                womanToPartner.put(woman, man);
            } else {
                LinkedList<String> womanPreferenceList = womenPreferences.get(woman);

                if (womanPreferenceList == null) {
                    continue;
                }

                boolean prefersNewMan =
                        womanPreferenceList.indexOf(man) < womanPreferenceList.indexOf(currentPartner);

                if (prefersNewMan) {
                    womanToPartner.put(woman, man);
                    freeMen.add(currentPartner);
                } else {
                    freeMen.add(man);
                }
            }
        }
        return womanToPartner;
    }
}