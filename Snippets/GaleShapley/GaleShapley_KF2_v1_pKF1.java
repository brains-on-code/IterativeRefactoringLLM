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

        Map<String, String> engagements = new HashMap<>();
        LinkedList<String> freeMenQueue = new LinkedList<>(menPreferences.keySet());

        while (!freeMenQueue.isEmpty()) {
            String currentMan = freeMenQueue.poll();
            LinkedList<String> currentManPreferences = menPreferences.get(currentMan);

            if (currentManPreferences == null || currentManPreferences.isEmpty()) {
                continue;
            }

            String preferredWoman = currentManPreferences.poll();
            String currentFiance = engagements.get(preferredWoman);

            if (currentFiance == null) {
                engagements.put(preferredWoman, currentMan);
            } else {
                LinkedList<String> womanPreferenceList = womenPreferences.get(preferredWoman);

                if (womanPreferenceList == null) {
                    continue;
                }

                boolean prefersNewMan =
                        womanPreferenceList.indexOf(currentMan) < womanPreferenceList.indexOf(currentFiance);

                if (prefersNewMan) {
                    engagements.put(preferredWoman, currentMan);
                    freeMenQueue.add(currentFiance);
                } else {
                    freeMenQueue.add(currentMan);
                }
            }
        }
        return engagements;
    }
}