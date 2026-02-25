package com.thealgorithms.greedyalgorithms;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;


public final class GaleShapley {

    private GaleShapley() {
    }


    public static Map<String, String> stableMatch(Map<String, LinkedList<String>> womenPrefs, Map<String, LinkedList<String>> menPrefs) {
        Map<String, String> engagements = new HashMap<>();
        LinkedList<String> freeMen = new LinkedList<>(menPrefs.keySet());

        while (!freeMen.isEmpty()) {
            String man = freeMen.poll();
            LinkedList<String> manPref = menPrefs.get(man);

            if (manPref == null || manPref.isEmpty()) {
                continue;
            }

            String woman = manPref.poll();
            String fiance = engagements.get(woman);

            if (fiance == null) {
                engagements.put(woman, man);
            } else {
                LinkedList<String> womanPrefList = womenPrefs.get(woman);

                if (womanPrefList == null) {
                    continue;
                }

                if (womanPrefList.indexOf(man) < womanPrefList.indexOf(fiance)) {
                    engagements.put(woman, man);
                    freeMen.add(fiance);
                } else {
                    freeMen.add(man);
                }
            }
        }
        return engagements;
    }
}
