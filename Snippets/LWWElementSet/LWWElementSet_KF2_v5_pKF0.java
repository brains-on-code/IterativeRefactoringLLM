package com.thealgorithms.datastructures.crdt;

import java.util.HashMap;
import java.util.Map;

final class Element {
    private final String key;
    private final int timestamp;
    private final Bias bias;

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
        addSet.put(element.getKey(), element);
    }

    public void remove(Element element) {
        if (contains(element)) {
            removeSet.put(element.getKey(), element);
        }
    }

    public boolean contains(Element element) {
        Element addedElement = addSet.get(element.getKey());
        if (addedElement == null) {
            return false;
        }

        Element removedElement = removeSet.get(element.getKey());
        if (removedElement == null) {
            return true;
        }

        return isAdditionPreferred(addedElement, removedElement);
    }

    private boolean isAdditionPreferred(Element addedElement, Element removedElement) {
        return addedElement.getTimestamp() > removedElement.getTimestamp();
    }

    public boolean compare(LWWElementSet other) {
        return other.addSet.keySet().containsAll(addSet.keySet())
            && other.removeSet.keySet().containsAll(removeSet.keySet());
    }

    public void merge(LWWElementSet other) {
        mergeSet(addSet, other.addSet);
        mergeSet(removeSet, other.removeSet);
    }

    private void mergeSet(Map<String, Element> target, Map<String, Element> source) {
        for (Element element : source.values()) {
            target.merge(
                element.getKey(),
                element,
                (current, incoming) -> isIncomingPreferred(current, incoming) ? incoming : current
            );
        }
    }

    private boolean isIncomingPreferred(Element current, Element incoming) {
        if (current.getBias() != incoming.getBias()) {
            throw new IllegalArgumentException("Mismatched bias between elements");
        }

        int currentTimestamp = current.getTimestamp();
        int incomingTimestamp = incoming.getTimestamp();

        if (currentTimestamp == incomingTimestamp) {
            return incoming.getBias() == Bias.REMOVALS;
        }

        return incomingTimestamp > currentTimestamp;
    }
}