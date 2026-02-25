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

        Map<String, String> womanToPartnerMap = new HashMap<>();
        LinkedList<String> freeMenQueue = new LinkedList<>(menPreferences.keySet());

        while (!freeMenQueue.isEmpty()) {
            String man = freeMenQueue.poll();
            LinkedList<String> currentManPreferences = menPreferences.get(man);

            if (currentManPreferences == null || currentManPreferences.isEmpty()) {
                continue;
            }

            String woman = currentManPreferences.poll();
            String currentFiance = womanToPartnerMap.get(woman);

            if (currentFiance == null) {
                womanToPartnerMap.put(woman, man);
            } else {
                LinkedList<String> currentWomanPreferences = womenPreferences.get(woman);

                if (currentWomanPreferences == null) {
                    continue;
                }

                boolean prefersNewMan =
                        currentWomanPreferences.indexOf(man) < currentWomanPreferences.indexOf(currentFiance);

                if (prefersNewMan) {
                    womanToPartnerMap.put(woman, man);
                    freeMenQueue.add(currentFiance);
                } else {
                    freeMenQueue.add(man);
                }
            }
        }
        return womanToPartnerMap;
    }
}