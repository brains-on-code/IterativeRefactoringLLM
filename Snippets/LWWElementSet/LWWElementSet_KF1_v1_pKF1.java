package com.thealgorithms.datastructures.crdt;

import java.util.HashMap;
import java.util.Map;

class Operation {
    String elementId;
    int timestamp;
    Bias bias;

    Operation(String elementId, int timestamp, Bias bias) {
        this.elementId = elementId;
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
        addSet.put(operation.elementId, operation);
    }

    public void remove(Operation operation) {
        if (isRemovable(operation)) {
            removeSet.put(operation.elementId, operation);
        }
    }

    public boolean isRemovable(Operation operation) {
        Operation addOperation = addSet.get(operation.elementId);
        Operation removeOperation = removeSet.get(operation.elementId);

        return addOperation != null && (removeOperation == null || addOperation.timestamp > removeOperation.timestamp);
    }

    public boolean isSubsetOf(LwwElementSet other) {
        return other.addSet.keySet().containsAll(addSet.keySet())
            && other.removeSet.keySet().containsAll(removeSet.keySet());
    }

    public void merge(LwwElementSet other) {
        for (Operation otherAddOp : other.addSet.values()) {
            if (!addSet.containsKey(otherAddOp.elementId)
                || isNewerOrEqual(addSet.get(otherAddOp.elementId), otherAddOp)) {
                addSet.put(otherAddOp.elementId, otherAddOp);
            }
        }

        for (Operation otherRemoveOp : other.removeSet.values()) {
            if (!removeSet.containsKey(otherRemoveOp.elementId)
                || isNewerOrEqual(removeSet.get(otherRemoveOp.elementId), otherRemoveOp)) {
                removeSet.put(otherRemoveOp.elementId, otherRemoveOp);
            }
        }
    }

    public boolean isNewerOrEqual(Operation existingOperation, Operation newOperation) {
        if (existingOperation.bias != newOperation.bias) {
            throw new IllegalArgumentException("Invalid bias value");
        }

        Bias bias = existingOperation.bias;
        int comparison = Integer.compare(existingOperation.timestamp, newOperation.timestamp);

        if (comparison == 0) {
            return bias != Bias.ADDS;
        }
        return comparison < 0;
    }
}