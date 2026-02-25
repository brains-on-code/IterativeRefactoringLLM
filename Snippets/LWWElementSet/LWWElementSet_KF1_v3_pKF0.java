package com.thealgorithms.datastructures.crdt;

import java.util.HashMap;
import java.util.Map;

final class Operation {
    private final String key;
    private final int timestamp;
    private final Bias bias;

    Operation(String key, int timestamp, Bias bias) {
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

class LwwElementSet {
    private final Map<String, Operation> addSet;
    private final Map<String, Operation> removeSet;

    LwwElementSet() {
        this.addSet = new HashMap<>();
        this.removeSet = new HashMap<>();
    }

    public void add(Operation operation) {
        addSet.put(operation.getKey(), operation);
    }

    public void remove(Operation operation) {
        if (isNewerThanCurrent(operation)) {
            removeSet.put(operation.getKey(), operation);
        }
    }

    public boolean isNewerThanCurrent(Operation operation) {
        Operation currentAdd = addSet.get(operation.getKey());
        if (currentAdd == null) {
            return false;
        }

        Operation currentRemove = removeSet.get(operation.getKey());
        return currentRemove == null || currentAdd.getTimestamp() > currentRemove.getTimestamp();
    }

    public boolean isSubsetOf(LwwElementSet other) {
        return other.addSet.keySet().containsAll(addSet.keySet())
            && other.removeSet.keySet().containsAll(removeSet.keySet());
    }

    public void merge(LwwElementSet other) {
        mergeOperations(addSet, other.addSet);
        mergeOperations(removeSet, other.removeSet);
    }

    private void mergeOperations(Map<String, Operation> target, Map<String, Operation> source) {
        for (Operation incoming : source.values()) {
            target.merge(
                incoming.getKey(),
                incoming,
                (existing, newOp) -> isNewer(existing, newOp) ? newOp : existing
            );
        }
    }

    public boolean isNewer(Operation existing, Operation incoming) {
        if (existing.getBias() != incoming.getBias()) {
            throw new IllegalArgumentException("Operations must have the same bias");
        }

        int comparison = Integer.compare(existing.getTimestamp(), incoming.getTimestamp());
        if (comparison == 0) {
            return existing.getBias() != Bias.ADDS;
        }
        return comparison < 0;
    }
}