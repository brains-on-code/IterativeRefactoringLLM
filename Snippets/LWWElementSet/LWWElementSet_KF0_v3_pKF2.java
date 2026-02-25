package com.thealgorithms.datastructures.crdt;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a single operation (add or remove) on an element in the LWWElementSet.
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
 * Bias used when resolving concurrent operations with identical timestamps.
 */
enum Bias {
    ADDS,
    REMOVALS
}

/**
 * Last-Write-Wins Element Set (LWWElementSet) is a state-based CRDT for managing
 * sets in distributed and concurrent environments. It tracks additions and removals
 * with timestamps and resolves conflicts by preferring the operation with the
 * latest timestamp.
 *
 * The set is represented by two internal maps:
 * - addSet: elements that have been added
 * - removeSet: elements that have been removed
 */
class LWWElementSet {
    private final Map<String, Element> addSet;
    private final Map<String, Element> removeSet;

    LWWElementSet() {
        this.addSet = new HashMap<>();
        this.removeSet = new HashMap<>();
    }

    /**
     * Records an add operation for the given element.
     *
     * @param element the element to add
     */
    public void add(Element element) {
        addSet.put(element.key, element);
    }

    /**
     * Records a remove operation for the given element, but only if the element
     * is currently considered present in the set.
     *
     * @param element the element to remove
     */
    public void remove(Element element) {
        if (lookup(element)) {
            removeSet.put(element.key, element);
        }
    }

    /**
     * Checks whether the given element is currently present in the set.
     * Presence is determined by comparing the latest add and remove timestamps.
     *
     * @param element the element to look up
     * @return true if the element is present, false otherwise
     */
    public boolean lookup(Element element) {
        Element added = addSet.get(element.key);
        Element removed = removeSet.get(element.key);

        return added != null && (removed == null || added.timestamp > removed.timestamp);
    }

    /**
     * Checks whether this set is a subset of the given set, considering both
     * add and remove maps (by keys only).
     *
     * @param other the set to compare against
     * @return true if this set is a subset of {@code other}, false otherwise
     */
    public boolean compare(LWWElementSet other) {
        return other.addSet.keySet().containsAll(addSet.keySet())
            && other.removeSet.keySet().containsAll(removeSet.keySet());
    }

    /**
     * Merges another LWWElementSet into this one, keeping the element with the
     * latest timestamp for each key. Ties are resolved using the element bias.
     *
     * @param other the set to merge into this one
     */
    public void merge(LWWElementSet other) {
        mergeAddSet(other);
        mergeRemoveSet(other);
    }

    private void mergeAddSet(LWWElementSet other) {
        for (Element element : other.addSet.values()) {
            Element current = addSet.get(element.key);
            if (current == null || isNewer(current, element)) {
                addSet.put(element.key, element);
            }
        }
    }

    private void mergeRemoveSet(LWWElementSet other) {
        for (Element element : other.removeSet.values()) {
            Element current = removeSet.get(element.key);
            if (current == null || isNewer(current, element)) {
                removeSet.put(element.key, element);
            }
        }
    }

    /**
     * Determines whether {@code candidate} should replace {@code current},
     * based on timestamp and bias.
     *
     * @param current   the existing element
     * @param candidate the candidate element
     * @return true if {@code candidate} is considered newer, false otherwise
     */
    public boolean isNewer(Element current, Element candidate) {
        validateSameBias(current, candidate);

        int comparison = Integer.compare(current.timestamp, candidate.timestamp);

        if (comparison == 0) {
            return preferRemovalOnTie(current);
        }

        return comparison < 0;
    }

    private void validateSameBias(Element current, Element candidate) {
        if (current.bias != candidate.bias) {
            throw new IllegalArgumentException("Elements must have the same bias for comparison");
        }
    }

    private boolean preferRemovalOnTie(Element current) {
        return current.bias != Bias.ADDS;
    }
}