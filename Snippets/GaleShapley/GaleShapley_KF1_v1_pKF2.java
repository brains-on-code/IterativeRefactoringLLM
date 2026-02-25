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
        // Final matching: receiver -> proposer
        Map<String, String> matching = new HashMap<>();

        // Queue of proposers that are still free and have receivers left to propose to
        LinkedList<String> freeProposers = new LinkedList<>(proposalsByProposer.keySet());

        while (!freeProposers.isEmpty()) {
            String proposer = freeProposers.poll();
            LinkedList<String> proposerPreferences = proposalsByProposer.get(proposer);

            // If this proposer has no one left to propose to, skip
            if (proposerPreferences == null || proposerPreferences.isEmpty()) {
                continue;
            }

            // Propose to the next receiver on this proposer's list
            String receiver = proposerPreferences.poll();
            String currentMatch = matching.get(receiver);

            // If the receiver is free, match them with this proposer
            if (currentMatch == null) {
                matching.put(receiver, proposer);
            } else {
                // Receiver is currently matched; check if they prefer the new proposer
                LinkedList<String> receiverPreferences = preferencesByReceiver.get(receiver);

                if (receiverPreferences == null) {
                    // If the receiver has no preference list, keep the current match
                    continue;
                }

                // Lower index means higher preference
                if (receiverPreferences.indexOf(proposer) < receiverPreferences.indexOf(currentMatch)) {
                    // Receiver prefers the new proposer; update the match
                    matching.put(receiver, proposer);
                    // The old proposer becomes free again
                    freeProposers.add(currentMatch);
                } else {
                    // Receiver prefers their current match; proposer remains free
                    freeProposers.add(proposer);
                }
            }
        }

        return matching;
    }
}