package com.thealgorithms.datastructures.crdt;

import java.util.HashMap;
import java.util.Map;

/**
 * Last-Write-Wins Element Set (LWWElementSet) is a state-based CRDT (Conflict-free Replicated Data Type)
 * designed for managing sets in a distributed and concurrent environment. It supports the addition and removal
 * of elements, using timestamps to determine the order of operations. The set is split into two subsets:
 * the add set for elements to be added and the remove set for elements to be removed.
 *
 * @author itakurah (Niklas Hoefflin)
 * @see <a href="https://en.wikipedia.org/wiki/Conflict-free_replicated_data_type">Conflict-free_replicated_data_type</a>
 * @see <a href="https://github.com/itakurah">itakurah (Niklas Hoefflin)</a>
 */
final class Element {
    private final String key;
    private final int timestamp;
    private final Bias bias;

    /**
     * Constructs a new Element with the specified key, timestamp and bias.
     *
     * @param key       The key of the element.
     * @param timestamp The timestamp associated with the element.
     * @param bias      The bias of the element (ADDS or REMOVALS).
     */
    Element(String key, int timestamp, Bias bias) {
        this.key = key;
        this.timestamp = timestamp;
        this.bias = bias;
    }

    String getKey() {
        return key;
    }

    int getTimestamp() {
        return timestamp;
    }

    Bias getBias() {
        return bias;
    }
}

enum Bias {
    /**
     * ADDS bias for the add set.
     * REMOVALS bias for the remove set.
     */
    ADDS,
    REMOVALS
}

class LWWElementSet {
    private final Map<String, Element> addSet;
    private final Map<String, Element> removeSet;

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
    public void add(Element element) {
        addSet.put(element.getKey(), element);
    }

    /**
     * Removes an element from the removeSet.
     *
     * @param element The element to be removed.
     */
    public void remove(Element element) {
        if (contains(element)) {
            removeSet.put(element.getKey(), element);
        }
    }

    /**
     * Checks if an element is in the LWWElementSet by comparing timestamps in the addSet and removeSet.
     *
     * @param element The element to be checked.
     * @return True if the element is present, false otherwise.
     */
    public boolean contains(Element element) {
        Element addedElement = addSet.get(element.getKey());
        Element removedElement = removeSet.get(element.getKey());

        if (addedElement == null) {
            return false;
        }
        if (removedElement == null) {
            return true;
        }

        return addedElement.getTimestamp() > removedElement.getTimestamp();
    }

    /**
     * Compares the LWWElementSet with another LWWElementSet to check if addSet and removeSet are a subset.
     *
     * @param other The LWWElementSet to compare.
     * @return True if the set is subset, false otherwise.
     */
    public boolean isSubsetOf(LWWElementSet other) {
        return other.addSet.keySet().containsAll(addSet.keySet())
            && other.removeSet.keySet().containsAll(removeSet.keySet());
    }

    /**
     * Merges another LWWElementSet into this set by resolving conflicts based on timestamps.
     *
     * @param other The LWWElementSet to merge.
     */
    public void merge(LWWElementSet other) {
        for (Element element : other.addSet.values()) {
            mergeElement(addSet, element);
        }

        for (Element element : other.removeSet.values()) {
            mergeElement(removeSet, element);
        }
    }

    private void mergeElement(Map<String, Element> targetSet, Element incoming) {
        Element existing = targetSet.get(incoming.getKey());
        if (existing == null || isNewer(existing, incoming)) {
            targetSet.put(incoming.getKey(), incoming);
        }
    }

    /**
     * Compares timestamps of two elements based on their bias (ADDS or REMOVALS).
     *
     * @param current  The current element in the set.
     * @param incoming The incoming element to compare.
     * @return True if the incoming element should replace the current one.
     */
    public boolean isNewer(Element current, Element incoming) {
        if (current.getBias() != incoming.getBias()) {
            throw new IllegalArgumentException("Elements must have the same bias for comparison");
        }

        int comparison = Integer.compare(current.getTimestamp(), incoming.getTimestamp());

        if (comparison == 0) {
            // If timestamps are equal, removals win over adds
            return incoming.getBias() == Bias.REMOVALS;
        }

        // Incoming is newer if it has a greater timestamp
        return comparison < 0;
    }
}