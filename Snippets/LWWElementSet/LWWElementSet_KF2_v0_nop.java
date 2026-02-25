package com.thealgorithms.datastructures.crdt;

import java.util.HashMap;
import java.util.Map;



class Element {
    String key;
    int timestamp;
    Bias bias;


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


    public void add(Element e) {
        addSet.put(e.key, e);
    }


    public void remove(Element e) {
        if (lookup(e)) {
            removeSet.put(e.key, e);
        }
    }


    public boolean lookup(Element e) {
        Element inAddSet = addSet.get(e.key);
        Element inRemoveSet = removeSet.get(e.key);

        return (inAddSet != null && (inRemoveSet == null || inAddSet.timestamp > inRemoveSet.timestamp));
    }


    public boolean compare(LWWElementSet other) {
        return other.addSet.keySet().containsAll(addSet.keySet()) && other.removeSet.keySet().containsAll(removeSet.keySet());
    }


    public void merge(LWWElementSet other) {
        for (Element e : other.addSet.values()) {
            if (!addSet.containsKey(e.key) || compareTimestamps(addSet.get(e.key), e)) {
                addSet.put(e.key, e);
            }
        }

        for (Element e : other.removeSet.values()) {
            if (!removeSet.containsKey(e.key) || compareTimestamps(removeSet.get(e.key), e)) {
                removeSet.put(e.key, e);
            }
        }
    }


    public boolean compareTimestamps(Element e, Element other) {
        if (e.bias != other.bias) {
            throw new IllegalArgumentException("Invalid bias value");
        }
        Bias bias = e.bias;
        int timestampComparison = Integer.compare(e.timestamp, other.timestamp);

        if (timestampComparison == 0) {
            return bias != Bias.ADDS;
        }
        return timestampComparison < 0;
    }
}
