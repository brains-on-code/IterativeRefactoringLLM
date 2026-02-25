package com.thealgorithms.datastructures.crdt;

import java.util.HashMap;
import java.util.Map;

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

    public void add(Element element) {
        addSet.put(element.key, element);
    }

    public void remove(Element element) {
        if (lookup(element)) {
            removeSet.put(element.key, element);
        }
    }

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

    public boolean compare(LWWElementSet other) {
        return other.addSet.keySet().containsAll(addSet.keySet())
            && other.removeSet.keySet().containsAll(removeSet.keySet());
    }

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

    public boolean isNewer(Element current, Element candidate) {
        if (current.bias != candidate.bias) {
            throw new IllegalArgumentException("Elements must have the same bias to compare timestamps.");
        }

        int timestampComparison = Integer.compare(current.timestamp, candidate.timestamp);

        if (timestampComparison == 0) {
            return current.bias != Bias.ADDS;
        }

        return timestampComparison < 0;
    }
}