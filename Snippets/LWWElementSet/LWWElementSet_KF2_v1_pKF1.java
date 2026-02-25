package com.thealgorithms.datastructures.crdt;

import java.util.HashMap;
import java.util.Map;

class LwwElement {
    String key;
    int timestamp;
    Bias bias;

    LwwElement(String key, int timestamp, Bias bias) {
        this.key = key;
        this.timestamp = timestamp;
        this.bias = bias;
    }
}

enum Bias {
    ADDS,
    REMOVALS
}

class LwwElementSet {
    private final Map<String, LwwElement> addSet;
    private final Map<String, LwwElement> removeSet;

    LwwElementSet() {
        this.addSet = new HashMap<>();
        this.removeSet = new HashMap<>();
    }

    public void add(LwwElement element) {
        addSet.put(element.key, element);
    }

    public void remove(LwwElement element) {
        if (lookup(element)) {
            removeSet.put(element.key, element);
        }
    }

    public boolean lookup(LwwElement element) {
        LwwElement addedElement = addSet.get(element.key);
        LwwElement removedElement = removeSet.get(element.key);

        return addedElement != null
            && (removedElement == null || addedElement.timestamp > removedElement.timestamp);
    }

    public boolean compare(LwwElementSet otherSet) {
        return otherSet.addSet.keySet().containsAll(addSet.keySet())
            && otherSet.removeSet.keySet().containsAll(removeSet.keySet());
    }

    public void merge(LwwElementSet otherSet) {
        for (LwwElement otherAddedElement : otherSet.addSet.values()) {
            LwwElement currentAddedElement = addSet.get(otherAddedElement.key);
            if (currentAddedElement == null || isNewerOrBiased(currentAddedElement, otherAddedElement)) {
                addSet.put(otherAddedElement.key, otherAddedElement);
            }
        }

        for (LwwElement otherRemovedElement : otherSet.removeSet.values()) {
            LwwElement currentRemovedElement = removeSet.get(otherRemovedElement.key);
            if (currentRemovedElement == null || isNewerOrBiased(currentRemovedElement, otherRemovedElement)) {
                removeSet.put(otherRemovedElement.key, otherRemovedElement);
            }
        }
    }

    public boolean isNewerOrBiased(LwwElement currentElement, LwwElement otherElement) {
        if (currentElement.bias != otherElement.bias) {
            throw new IllegalArgumentException("Invalid bias value");
        }

        Bias bias = currentElement.bias;
        int timestampComparison = Integer.compare(currentElement.timestamp, otherElement.timestamp);

        if (timestampComparison == 0) {
            return bias != Bias.ADDS;
        }
        return timestampComparison < 0;
    }
}