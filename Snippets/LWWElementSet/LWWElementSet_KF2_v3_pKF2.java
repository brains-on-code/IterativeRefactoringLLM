package com.thealgorithms.datastructures.crdt;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a single element in the LWW-Element-Set CRDT.
 * Each element is identified by a key and has an associated timestamp and bias.
 */
class Element {
    final String key;
    final int timestamp;
    final Bias bias;

    Element(String key, int timestamp, Bias bias) {
        this.key = key;
        this.timestamp = timestamp;
        this.bias = bias;
    }
}

/**
 * Bias used to break ties when timestamps are equal.
 * ADDS     - favors additions
 * REMOVALS - favors removals
 */
enum Bias {
    ADDS,
    REMOVALS
}

/**
 * Last-Writer-Wins Element Set (LWW-Element-Set) CRDT implementation.
 * Maintains two maps: one for additions and one for removals.
 * For each key, the element with the latest timestamp determines membership.
 */
class LWWElementSet {
    private final Map<String, Element> addSet;
    private final Map<String, Element> removeSet;

    LWWElementSet() {
        this.addSet = new HashMap<>();
        this.removeSet = new HashMap<>();
    }

    /**
     * Adds or updates an element in the add-set.
     */
    public void add(Element element) {
        addSet.put(element.key, element);
    }

    /**
     * Adds or updates an element in the remove-set only if it is currently present.
     */
    public void remove(Element element) {
        if (lookup(element)) {
            removeSet.put(element.key, element);
        }
    }

    /**
     * Checks whether the given element's key is currently present
     * in the set according to LWW semantics.
     */
    public boolean lookup(Element element) {
        Element addedElement = addSet.get(element.key);
        Element removedElement = removeSet.get(element.key);

        if (addedElement == null) {
            return false;
        }
        if (removedElement == null) {
            return true;
        }
        return addedElement.timestamp > removedElement.timestamp;
    }

    /**
     * Returns true if this set is a subset of the other set
     * with respect to both add and remove maps.
     */
    public boolean compare(LWWElementSet other) {
        return other.addSet.keySet().containsAll(addSet.keySet())
            && other.removeSet.keySet().containsAll(removeSet.keySet());
    }

    /**
     * Merges another LWWElementSet into this one by taking, for each key,
     * the element with the newer timestamp (or applying bias on ties).
     */
    public void merge(LWWElementSet other) {
        for (Element otherAdded : other.addSet.values()) {
            Element currentAdded = addSet.get(otherAdded.key);
            if (currentAdded == null || isNewer(currentAdded, otherAdded)) {
                addSet.put(otherAdded.key, otherAdded);
            }
        }

        for (Element otherRemoved : other.removeSet.values()) {
            Element currentRemoved = removeSet.get(otherRemoved.key);
            if (currentRemoved == null || isNewer(currentRemoved, otherRemoved)) {
                removeSet.put(otherRemoved.key, otherRemoved);
            }
        }
    }

    /**
     * Determines whether the candidate element is considered newer than the current one.
     * Elements must share the same bias. If timestamps are equal, the bias decides:
     * - If bias is ADDS, additions win on ties.
     * - If bias is REMOVALS, removals win on ties.
     */
    public boolean isNewer(Element current, Element candidate) {
        if (current.bias != candidate.bias) {
            throw new IllegalArgumentException("Elements must have the same bias to compare timestamps.");
        }

        int timestampComparison = Integer.compare(current.timestamp, candidate.timestamp);

        if (timestampComparison == 0) {
            // On equal timestamps, the element whose bias is favored is considered newer.
            return current.bias != Bias.ADDS;
        }

        return timestampComparison < 0;
    }
}