package com.thealgorithms.datastructures.crdt;

import java.util.HashMap;
import java.util.Map;

class LwwElement {
    String elementKey;
    int timestamp;
    Bias bias;

    LwwElement(String elementKey, int timestamp, Bias bias) {
        this.elementKey = elementKey;
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
        addSet.put(element.elementKey, element);
    }

    public void remove(LwwElement element) {
        if (lookup(element)) {
            removeSet.put(element.elementKey, element);
        }
    }

    public boolean lookup(LwwElement element) {
        LwwElement addedElement = addSet.get(element.elementKey);
        LwwElement removedElement = removeSet.get(element.elementKey);

        return addedElement != null
            && (removedElement == null || addedElement.timestamp > removedElement.timestamp);
    }

    public boolean compare(LwwElementSet otherSet) {
        return otherSet.addSet.keySet().containsAll(addSet.keySet())
            && otherSet.removeSet.keySet().containsAll(removeSet.keySet());
    }

    public void merge(LwwElementSet otherSet) {
        for (LwwElement incomingAddedElement : otherSet.addSet.values()) {
            LwwElement existingAddedElement = addSet.get(incomingAddedElement.elementKey);
            if (existingAddedElement == null || isNewerOrBiased(existingAddedElement, incomingAddedElement)) {
                addSet.put(incomingAddedElement.elementKey, incomingAddedElement);
            }
        }

        for (LwwElement incomingRemovedElement : otherSet.removeSet.values()) {
            LwwElement existingRemovedElement = removeSet.get(incomingRemovedElement.elementKey);
            if (existingRemovedElement == null || isNewerOrBiased(existingRemovedElement, incomingRemovedElement)) {
                removeSet.put(incomingRemovedElement.elementKey, incomingRemovedElement);
            }
        }
    }

    public boolean isNewerOrBiased(LwwElement existingElement, LwwElement incomingElement) {
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