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
        LinkedList<String> unmatchedProposers = new LinkedList<>(proposerPreferenceLists.keySet());

        while (!unmatchedProposers.isEmpty()) {
            String proposer = unmatchedProposers.poll();
            LinkedList<String> proposerPreferenceList = proposerPreferenceLists.get(proposer);

            if (proposerPreferenceList == null || proposerPreferenceList.isEmpty()) {
                continue;
            }

            String candidate = proposerPreferenceList.poll();
            String currentlyMatchedProposer = candidateToProposerMatch.get(candidate);

            if (currentlyMatchedProposer == null) {
                candidateToProposerMatch.put(candidate, proposer);
            } else {
                LinkedList<String> candidatePreferenceList = candidatePreferenceRanking.get(candidate);

                if (candidatePreferenceList == null) {
                    continue;
                }

                boolean candidatePrefersNewProposer =
                        candidatePreferenceList.indexOf(proposer)
                                < candidatePreferenceList.indexOf(currentlyMatchedProposer);

                if (candidatePrefersNewProposer) {
                    candidateToProposerMatch.put(candidate, proposer);
                    unmatchedProposers.add(currentlyMatchedProposer);
                } else {
                    unmatchedProposers.add(proposer);
                }
            }
        }
        return candidateToProposerMatch;
    }
}