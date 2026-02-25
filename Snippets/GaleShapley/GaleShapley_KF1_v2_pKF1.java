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
     * @param candidatePreferenceRanking mapping from candidate to their ordered list of preferred proposers
     * @param proposerPreferenceLists    mapping from proposer to their ordered list of preferred candidates
     * @return mapping from candidate to the proposer they are matched with
     */
    public static Map<String, String> computeStableMatching(
            Map<String, LinkedList<String>> candidatePreferenceRanking,
            Map<String, LinkedList<String>> proposerPreferenceLists
    ) {
        Map<String, String> candidateToProposerMatch = new HashMap<>();
        LinkedList<String> freeProposers = new LinkedList<>(proposerPreferenceLists.keySet());

        while (!freeProposers.isEmpty()) {
            String proposer = freeProposers.poll();
            LinkedList<String> proposerPreferences = proposerPreferenceLists.get(proposer);

            if (proposerPreferences == null || proposerPreferences.isEmpty()) {
                continue;
            }

            String candidate = proposerPreferences.poll();
            String currentMatchedProposer = candidateToProposerMatch.get(candidate);

            if (currentMatchedProposer == null) {
                candidateToProposerMatch.put(candidate, proposer);
            } else {
                LinkedList<String> candidatePreferences = candidatePreferenceRanking.get(candidate);

                if (candidatePreferences == null) {
                    continue;
                }

                boolean prefersNewProposer =
                        candidatePreferences.indexOf(proposer)
                                < candidatePreferences.indexOf(currentMatchedProposer);

                if (prefersNewProposer) {
                    candidateToProposerMatch.put(candidate, proposer);
                    freeProposers.add(currentMatchedProposer);
                } else {
                    freeProposers.add(proposer);
                }
            }
        }
        return candidateToProposerMatch;
    }
}