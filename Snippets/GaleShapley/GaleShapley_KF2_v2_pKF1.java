package com.thealgorithms.greedyalgorithms;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public final class GaleShapley {

    private GaleShapley() {
    }

    public static Map<String, String> stableMatch(
            Map<String, LinkedList<String>> womenPreferenceLists,
            Map<String, LinkedList<String>> menPreferenceLists) {

        Map<String, String> womanToFianceMap = new HashMap<>();
        LinkedList<String> freeMen = new LinkedList<>(menPreferenceLists.keySet());

        while (!freeMen.isEmpty()) {
            String man = freeMen.poll();
            LinkedList<String> manPreferenceList = menPreferenceLists.get(man);

            if (manPreferenceList == null || manPreferenceList.isEmpty()) {
                continue;
            }

            String woman = manPreferenceList.poll();
            String currentFiance = womanToFianceMap.get(woman);

            if (currentFiance == null) {
                womanToFianceMap.put(woman, man);
            } else {
                LinkedList<String> womanPreferenceList = womenPreferenceLists.get(woman);

                if (womanPreferenceList == null) {
                    continue;
                }

                boolean womanPrefersNewMan =
                        womanPreferenceList.indexOf(man) < womanPreferenceList.indexOf(currentFiance);

                if (womanPrefersNewMan) {
                    womanToFianceMap.put(woman, man);
                    freeMen.add(currentFiance);
                } else {
                    freeMen.add(man);
                }
            }
        }
        return womanToFianceMap;
    }
}