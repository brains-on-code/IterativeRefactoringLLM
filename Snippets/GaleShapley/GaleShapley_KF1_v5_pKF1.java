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
        Map<String, String> candidateToProposerMatch = new HashMap<>();
        LinkedList<String> unmatchedProposers = new LinkedList<>(proposerPreferences.keySet());

        while (!unmatchedProposers.isEmpty()) {
            String proposer = unmatchedProposers.poll();
            LinkedList<String> proposerPreferenceQueue = proposerPreferences.get(proposer);

            if (proposerPreferenceQueue == null || proposerPreferenceQueue.isEmpty()) {
                continue;
            }

            String candidate = proposerPreferenceQueue.poll();
            String currentMatchedProposer = candidateToProposerMatch.get(candidate);

            if (currentMatchedProposer == null) {
                candidateToProposerMatch.put(candidate, proposer);
            } else {
                LinkedList<String> candidatePreferenceList = candidatePreferences.get(candidate);

                if (candidatePreferenceList == null) {
                    continue;
                }

                boolean candidatePrefersNewProposer =
                        candidatePreferenceList.indexOf(proposer)
                                < candidatePreferenceList.indexOf(currentMatchedProposer);

                if (candidatePrefersNewProposer) {
                    candidateToProposerMatch.put(candidate, proposer);
                    unmatchedProposers.add(currentMatchedProposer);
                } else {
                    unmatchedProposers.add(proposer);
                }
            }
        }
        return candidateToProposerMatch;
    }
}