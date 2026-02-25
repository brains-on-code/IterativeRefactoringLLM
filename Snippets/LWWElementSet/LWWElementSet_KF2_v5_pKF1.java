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
        if (contains(element)) {
            removeSet.put(element.key, element);
        }
    }

    public boolean contains(LwwElement element) {
        LwwElement addedElement = addSet.get(element.key);
        LwwElement removedElement = removeSet.get(element.key);

        return addedElement != null
            && (removedElement == null || addedElement.timestamp > removedElement.timestamp);
    }

    public boolean isSubsetOf(LwwElementSet otherSet) {
        return otherSet.addSet.keySet().containsAll(addSet.keySet())
            && otherSet.removeSet.keySet().containsAll(removeSet.keySet());
    }

    public void merge(LwwElementSet otherSet) {
        for (LwwElement incomingAddedElement : otherSet.addSet.values()) {
            LwwElement existingAddedElement = addSet.get(incomingAddedElement.key);
            if (existingAddedElement == null || isNewerOrPreferred(existingAddedElement, incomingAddedElement)) {
                addSet.put(incomingAddedElement.key, incomingAddedElement);
            }
        }

        for (LwwElement incomingRemovedElement : otherSet.removeSet.values()) {
            LwwElement existingRemovedElement = removeSet.get(incomingRemovedElement.key);
            if (existingRemovedElement == null || isNewerOrPreferred(existingRemovedElement, incomingRemovedElement)) {
                removeSet.put(incomingRemovedElement.key, incomingRemovedElement);
            }
        }
    }

    public boolean isNewerOrPreferred(LwwElement existingElement, LwwElement incomingElement) {
        if (existingElement.bias != incomingElement.bias) {
            throw new IllegalArgumentException("Invalid bias value");
        }

        Bias bias = existingElement.bias;
        int timestampComparison = Integer.compare(existingElement.timestamp, incomingElement.timestamp);

        if (timestampComparison == 0) {
            return bias != Bias.ADDS;
        }
        return timestampComparison < 0;
    }
}