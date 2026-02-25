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
     * @param preferencesByCandidate Map from candidate to an ordered list of preferred options.
     * @param preferencesByOption    Map from option to an ordered list of preferred candidates.
     * @return Map from option to the candidate it is matched with.
     */
    public static Map<String, String> method1(
            Map<String, LinkedList<String>> preferencesByCandidate,
            Map<String, LinkedList<String>> preferencesByOption) {

        Map<String, String> candidateToOptionMatch = new HashMap<>();
        LinkedList<String> freeOptions = new LinkedList<>(preferencesByOption.keySet());

        while (!freeOptions.isEmpty()) {
            String option = freeOptions.poll();
            LinkedList<String> optionPreferences = preferencesByOption.get(option);

            if (optionPreferences == null || optionPreferences.isEmpty()) {
                continue;
            }

            String candidate = optionPreferences.poll();
            String currentlyMatchedOption = candidateToOptionMatch.get(candidate);

            if (currentlyMatchedOption == null) {
                candidateToOptionMatch.put(candidate, option);
                continue;
            }

            LinkedList<String> candidatePreferences = preferencesByCandidate.get(candidate);
            if (candidatePreferences == null) {
                continue;
            }

            int newOptionRank = candidatePreferences.indexOf(option);
            int currentOptionRank = candidatePreferences.indexOf(currentlyMatchedOption);

            boolean candidatePrefersNewOption =
                    newOptionRank != -1 && (currentOptionRank == -1 || newOptionRank < currentOptionRank);

            if (candidatePrefersNewOption) {
                candidateToOptionMatch.put(candidate, option);
                freeOptions.add(currentlyMatchedOption);
            } else {
                freeOptions.add(option);
            }
        }

        return candidateToOptionMatch;
    }
}