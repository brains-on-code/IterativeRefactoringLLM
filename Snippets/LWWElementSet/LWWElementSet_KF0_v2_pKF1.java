package com.thealgorithms.datastructures.crdt;

import java.util.HashMap;
import java.util.Map;

/**
 * Last-Write-Wins Element Set (LWWElementSet) is a state-based CRDT (Conflict-free Replicated Data Type)
 * designed for managing sets in a distributed and concurrent environment. It supports the addition and removal
 * of elements, using timestamps to determine the order of operations. The set is split into two subsets:
 * the add set for elements to be added and the remove set for elements to be removed.
 *
 * @author itakurah (Niklas Hoefflin) (https://github.com/itakurah)
 * @see <a href="https://en.wikipedia.org/wiki/Conflict-free_replicated_data_type">Conflict-free_replicated_data_type</a>
 * @see <a href="https://github.com/itakurah">itakurah (Niklas Hoefflin)</a>
 */

class LwwElement {
    String id;
    int timestamp;
    LwwBias bias;

    /**
     * Constructs a new LwwElement with the specified id, timestamp and bias.
     *
     * @param id        The identifier of the element.
     * @param timestamp The timestamp associated with the element.
     * @param bias      The bias of the element (ADDS or REMOVALS).
     */
    LwwElement(String id, int timestamp, LwwBias bias) {
        this.id = id;
        this.timestamp = timestamp;
        this.bias = bias;
    }
}

enum LwwBias {
    /**
     * ADDS bias for the add set.
     * REMOVALS bias for the remove set.
     */
    ADDS,
    REMOVALS
}

class LWWElementSet {
    private final Map<String, LwwElement> addSet;
    private final Map<String, LwwElement> removeSet;

    /**
     * Constructs an empty LWWElementSet.
     */
    LWWElementSet() {
        this.addSet = new HashMap<>();
        this.removeSet = new HashMap<>();
    }

    /**
     * Adds an element to the addSet.
     *
     * @param element The element to be added.
     */
    public void add(LwwElement element) {
        addSet.put(element.id, element);
    }

    /**
     * Removes an element from the removeSet.
     *
     * @param element The element to be removed.
     */
    public void remove(LwwElement element) {
        if (lookup(element)) {
            removeSet.put(element.id, element);
        }
    }

    /**
     * Checks if an element is in the LWWElementSet by comparing timestamps in the addSet and removeSet.
     *
     * @param element The element to be checked.
     * @return True if the element is present, false otherwise.
     */
    public boolean lookup(LwwElement element) {
        LwwElement addedElement = addSet.get(element.id);
        LwwElement removedElement = removeSet.get(element.id);

        return addedElement != null && (removedElement == null || addedElement.timestamp > removedElement.timestamp);
    }

    /**
     * Compares the LWWElementSet with another LWWElementSet to check if addSet and removeSet are a subset.
     *
     * @param otherSet The LWWElementSet to compare.
     * @return True if the set is subset, false otherwise.
     */
    public boolean compare(LWWElementSet otherSet) {
        return otherSet.addSet.keySet().containsAll(addSet.keySet())
            && otherSet.removeSet.keySet().containsAll(removeSet.keySet());
    }

    /**
     * Merges another LWWElementSet into this set by resolving conflicts based on timestamps.
     *
     * @param otherSet The LWWElementSet to merge.
     */
    public void merge(LWWElementSet otherSet) {
        for (LwwElement otherAddedElement : otherSet.addSet.values()) {
            LwwElement currentAddedElement = addSet.get(otherAddedElement.id);
            if (currentAddedElement == null || isNewerOrBiased(otherAddedElement, currentAddedElement)) {
                addSet.put(otherAddedElement.id, otherAddedElement);
            }
        }

        for (LwwElement otherRemovedElement : otherSet.removeSet.values()) {
            LwwElement currentRemovedElement = removeSet.get(otherRemovedElement.id);
            if (currentRemovedElement == null || isNewerOrBiased(otherRemovedElement, currentRemovedElement)) {
                removeSet.put(otherRemovedElement.id, otherRemovedElement);
            }
        }
    }

    /**
     * Compares timestamps of two elements based on their bias (ADDS or REMOVALS).
     *
     * @param candidateElement The element that may replace the current element.
     * @param currentElement   The element currently stored.
     * @return True if the candidate element should replace the current element.
     */
    public boolean isNewerOrBiased(LwwElement candidateElement, LwwElement currentElement) {
        if (candidateElement.bias != currentElement.bias) {
            throw new IllegalArgumentException("Invalid bias value");
        }

        int timestampComparison = Integer.compare(currentElement.timestamp, candidateElement.timestamp);

        if (timestampComparison == 0) {
            return candidateElement.bias != LwwBias.ADDS;
        }
        return timestampComparison < 0;
    }
}