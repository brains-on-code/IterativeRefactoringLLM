package com.thealgorithms.datastructures.crdt;

import java.util.HashMap;
import java.util.Map;

class Operation {
    String key;
    int timestamp;
    Bias bias;

    Operation(String key, int timestamp, Bias bias) {
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
    private final Map<String, Operation> addSet;
    private final Map<String, Operation> removeSet;

    LwwElementSet() {
        this.addSet = new HashMap<>();
        this.removeSet = new HashMap<>();
    }

    public void add(Operation operation) {
        addSet.put(operation.key, operation);
    }

    public void remove(Operation operation) {
        if (isNewerThanCurrent(operation)) {
            removeSet.put(operation.key, operation);
        }
    }

    public boolean isNewerThanCurrent(Operation operation) {
        Operation currentAdd = addSet.get(operation.key);
        Operation currentRemove = removeSet.get(operation.key);

        return currentAdd != null && (currentRemove == null || currentAdd.timestamp > currentRemove.timestamp);
    }

    public boolean isSubsetOf(LwwElementSet other) {
        return other.addSet.keySet().containsAll(addSet.keySet())
            && other.removeSet.keySet().containsAll(removeSet.keySet());
    }

    public void merge(LwwElementSet other) {
        for (Operation operation : other.addSet.values()) {
            if (!addSet.containsKey(operation.key)
                || isNewer(operation, addSet.get(operation.key))) {
                addSet.put(operation.key, operation);
            }
        }

        for (Operation operation : other.removeSet.values()) {
            if (!removeSet.containsKey(operation.key)
                || isNewer(operation, removeSet.get(operation.key))) {
                removeSet.put(operation.key, operation);
            }
        }
    }

    public boolean isNewer(Operation existing, Operation incoming) {
        if (existing.bias != incoming.bias) {
            throw new IllegalArgumentException("Invalid bias value");
        }

        Bias bias = existing.bias;
        int comparison = Integer.compare(existing.timestamp, incoming.timestamp);

        if (comparison == 0) {
            return bias != Bias.ADDS;
        }
        return comparison < 0;
    }
}