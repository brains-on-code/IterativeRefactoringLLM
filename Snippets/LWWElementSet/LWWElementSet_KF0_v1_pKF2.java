package com.thealgorithms.datastructures.crdt;

import java.util.HashMap;
import java.util.Map;

/**
 * Last-Write-Wins Element Set (LWWElementSet) is a state-based CRDT (Conflict-free Replicated Data Type)
 * for managing sets in distributed and concurrent environments. It tracks additions and removals with
 * timestamps and resolves conflicts by preferring the operation with the latest timestamp.
 *
 * The set is represented by two internal maps:
 * - addSet: elements that have been added
 * - removeSet: elements that have been removed
 */
class Element {
    final String key;
    final int timestamp;
    final Bias bias;

    /**
     * Creates a new Element.
     *
     * @param key       element identifier
     * @param timestamp logical or physical timestamp of the operation
     * @param bias      operation type (ADDS or REMOVALS)
     */
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

class LWWElementSet {
    private final Map<String, Element> addSet;
    private final Map<String, Element> removeSet;

    LWWElementSet() {
        this.addSet = new HashMap<>();
        this.removeSet = new HashMap<>();
    }

    /**
     * Records an add operation for the given element.
     */
    public void add(Element e) {
        addSet.put(e.key, e);
    }

    /**
     * Records a remove operation for the given element, but only if the element
     * is currently considered present in the set.
     */
    public void remove(Element e) {
        if (lookup(e)) {
            removeSet.put(e.key, e);
        }
    }

    /**
     * Returns true if the element is currently considered present in the set.
     * Presence is determined by comparing the latest add and remove timestamps.
     */
    public boolean lookup(Element e) {
        Element added = addSet.get(e.key);
        Element removed = removeSet.get(e.key);

        return added != null && (removed == null || added.timestamp > removed.timestamp);
    }

    /**
     * Returns true if this set is a subset of the given set, considering both
     * add and remove maps (by keys only).
     */
    public boolean compare(LWWElementSet other) {
        return other.addSet.keySet().containsAll(addSet.keySet())
            && other.removeSet.keySet().containsAll(removeSet.keySet());
    }

    /**
     * Merges another LWWElementSet into this one, keeping the element with the
     * latest timestamp for each key. Ties are resolved using the element bias.
     */
    public void merge(LWWElementSet other) {
        for (Element e : other.addSet.values()) {
            Element current = addSet.get(e.key);
            if (current == null || isNewer(current, e)) {
                addSet.put(e.key, e);
            }
        }

        for (Element e : other.removeSet.values()) {
            Element current = removeSet.get(e.key);
            if (current == null || isNewer(current, e)) {
                removeSet.put(e.key, e);
            }
        }
    }

    /**
     * Returns true if {@code other} should replace {@code current}, based on
     * timestamp and bias.
     *
     * @param current existing element
     * @param other   candidate element
     */
    public boolean isNewer(Element current, Element other) {
        if (current.bias != other.bias) {
            throw new IllegalArgumentException("Elements must have the same bias for comparison");
        }

        int comparison = Integer.compare(current.timestamp, other.timestamp);

        if (comparison == 0) {
            // For equal timestamps, prefer REMOVALS over ADDS
            return current.bias != Bias.ADDS;
        }

        // Prefer element with the larger timestamp
        return comparison < 0;
    }
}