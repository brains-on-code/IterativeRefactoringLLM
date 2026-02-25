package com.thealgorithms.greedyalgorithms;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Implementation of a stable matching algorithm (Gale-Shapley style).
 */
public final class StableMatching {

    private StableMatching() {
    }

    /**
     * Computes a stable matching between proposers and candidates.
     *
     * @param candidatePreferences mapping from candidate to their ordered list of preferred proposers
     * @param proposerPreferences  mapping from proposer to their ordered list of preferred candidates
     * @return mapping from candidate to the proposer they are matched with
     */
    public static Map<String, String> computeStableMatching(
            Map<String, LinkedList<String>> candidatePreferences,
            Map<String, LinkedList<String>> proposerPreferences
    ) {
        Map<String, String> candidateToProposer = new HashMap<>();
        LinkedList<String> freeProposers = new LinkedList<>(proposerPreferences.keySet());

        while (!freeProposers.isEmpty()) {
            String proposer = freeProposers.poll();
            LinkedList<String> proposerPreferenceList = proposerPreferences.get(proposer);

            if (proposerPreferenceList == null || proposerPreferenceList.isEmpty()) {
                continue;
            }

            String candidate = proposerPreferenceList.poll();
            String currentPartner = candidateToProposer.get(candidate);

            if (currentPartner == null) {
                candidateToProposer.put(candidate, proposer);
            } else {
                LinkedList<String> candidatePreferenceList = candidatePreferences.get(candidate);

                if (candidatePreferenceList == null) {
                    continue;
                }

                boolean prefersNewProposer =
                        candidatePreferenceList.indexOf(proposer)
                                < candidatePreferenceList.indexOf(currentPartner);

                if (prefersNewProposer) {
                    candidateToProposer.put(candidate, proposer);
                    freeProposers.add(currentPartner);
                } else {
                    freeProposers.add(proposer);
                }
            }
        }
        return candidateToProposer;
    }
}