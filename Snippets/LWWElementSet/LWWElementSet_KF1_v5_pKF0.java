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
        if (isPresentAndNewerThanRemove(operation)) {
            removeSet.put(operation.getKey(), operation);
        }
    }

    private boolean isPresentAndNewerThanRemove(Operation operation) {
        String key = operation.getKey();
        Operation currentAdd = addSet.get(key);
        if (currentAdd == null) {
            return false;
        }

        Operation currentRemove = removeSet.get(key);
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
                (existing, newOp) -> isIncomingNewer(existing, newOp) ? newOp : existing
            );
        }
    }

    private boolean isIncomingNewer(Operation existing, Operation incoming) {
        if (existing.getBias() != incoming.getBias()) {
            throw new IllegalArgumentException("Operations must have the same bias");
        }

        int existingTimestamp = existing.getTimestamp();
        int incomingTimestamp = incoming.getTimestamp();

        if (existingTimestamp == incomingTimestamp) {
            // On tie, prefer non-ADD bias (i.e., REMOVALS wins over ADDS)
            return existing.getBias() != Bias.ADDS;
        }

        return existingTimestamp < incomingTimestamp;
    }
}