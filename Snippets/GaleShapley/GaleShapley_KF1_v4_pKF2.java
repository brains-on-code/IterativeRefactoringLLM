package com.thealgorithms.greedyalgorithms;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Utility class for performing a stable matching between two sets of entities
 * using their preference lists.
 *
 * <p>This implementation is structurally similar to the Gale–Shapley
 * stable matching algorithm. One side (the "proposers") iteratively proposes
 * to the other side (the "receivers") based on their preference lists, and
 * receivers keep their most preferred current proposal.</p>
 */
public final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Computes a stable matching between two sets of entities.
     *
     * @param preferencesByReceiver
     *     A map from each receiver to a ranked list of proposers, ordered from
     *     most preferred to least preferred.
     * @param proposalsByProposer
     *     A map from each proposer to a queue (list) of receivers they will
     *     propose to, in order of preference.
     * @return
     *     A map from each receiver to the proposer they are matched with.
     */
    public static Map<String, String> method1(
        Map<String, LinkedList<String>> preferencesByReceiver,
        Map<String, LinkedList<String>> proposalsByProposer
    ) {
        Map<String, String> matchingByReceiver = new HashMap<>();
        LinkedList<String> freeProposers = new LinkedList<>(proposalsByProposer.keySet());

        while (!freeProposers.isEmpty()) {
            String proposer = freeProposers.poll();
            LinkedList<String> proposerPreferenceQueue = proposalsByProposer.get(proposer);

            if (proposerPreferenceQueue == null || proposerPreferenceQueue.isEmpty()) {
                continue;
            }

            String receiver = proposerPreferenceQueue.poll();
            String currentProposerForReceiver = matchingByReceiver.get(receiver);

            if (currentProposerForReceiver == null) {
                matchingByReceiver.put(receiver, proposer);
                continue;
            }

            LinkedList<String> receiverPreferences = preferencesByReceiver.get(receiver);
            if (receiverPreferences == null) {
                continue;
            }

            boolean receiverPrefersNewProposer =
                receiverPreferences.indexOf(proposer)
                    < receiverPreferences.indexOf(currentProposerForReceiver);

            if (receiverPrefersNewProposer) {
                matchingByReceiver.put(receiver, proposer);
                freeProposers.add(currentProposerForReceiver);
            } else {
                freeProposers.add(proposer);
            }
        }

        return matchingByReceiver;
    }
}