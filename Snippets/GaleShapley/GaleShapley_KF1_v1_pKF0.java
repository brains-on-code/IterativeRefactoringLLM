package com.thealgorithms.greedyalgorithms;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Utility class for performing preference-based matching.
 */
public final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Performs a stable-style matching between two sets based on their preferences.
     *
     * @param preferencesByCandidate
     *         Map from candidate to an ordered list of preferred options.
     * @param preferencesByOption
     *         Map from option to an ordered list of preferred candidates.
     * @return Map from option to the candidate it is matched with.
     */
    public static Map<String, String> method1(
            Map<String, LinkedList<String>> preferencesByCandidate,
            Map<String, LinkedList<String>> preferencesByOption) {

        Map<String, String> matches = new HashMap<>();
        LinkedList<String> freeOptionsQueue = new LinkedList<>(preferencesByOption.keySet());

        while (!freeOptionsQueue.isEmpty()) {
            String option = freeOptionsQueue.poll();
            LinkedList<String> optionPreferences = preferencesByOption.get(option);

            if (optionPreferences == null || optionPreferences.isEmpty()) {
                continue;
            }

            String candidate = optionPreferences.poll();
            String currentMatchForCandidate = matches.get(candidate);

            if (currentMatchForCandidate == null) {
                matches.put(candidate, option);
            } else {
                LinkedList<String> candidatePreferences = preferencesByCandidate.get(candidate);

                if (candidatePreferences == null) {
                    continue;
                }

                int newOptionRank = candidatePreferences.indexOf(option);
                int currentOptionRank = candidatePreferences.indexOf(currentMatchForCandidate);

                if (newOptionRank < currentOptionRank) {
                    matches.put(candidate, option);
                    freeOptionsQueue.add(currentMatchForCandidate);
                } else {
                    freeOptionsQueue.add(option);
                }
            }
        }

        return matches;
    }
}