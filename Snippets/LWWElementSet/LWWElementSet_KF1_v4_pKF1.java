package com.thealgorithms.datastructures.crdt;

import java.util.HashMap;
import java.util.Map;

class ElementOperation {
    String key;
    int timestamp;
    Bias bias;

    ElementOperation(String key, int timestamp, Bias bias) {
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
    private final Map<String, ElementOperation> addOperationsByKey;
    private final Map<String, ElementOperation> removeOperationsByKey;

    LwwElementSet() {
        this.addOperationsByKey = new HashMap<>();
        this.removeOperationsByKey = new HashMap<>();
    }

    public void add(ElementOperation addOperation) {
        addOperationsByKey.put(addOperation.key, addOperation);
    }

    public void remove(ElementOperation removeOperation) {
        if (canRemove(removeOperation)) {
            removeOperationsByKey.put(removeOperation.key, removeOperation);
        }
    }

    public boolean canRemove(ElementOperation removeOperation) {
        ElementOperation lastAddOperation = addOperationsByKey.get(removeOperation.key);
        ElementOperation lastRemoveOperation = removeOperationsByKey.get(removeOperation.key);

        return lastAddOperation != null
            && (lastRemoveOperation == null || lastAddOperation.timestamp > lastRemoveOperation.timestamp);
    }

    public boolean isSubsetOf(LwwElementSet otherSet) {
        return otherSet.addOperationsByKey.keySet().containsAll(addOperationsByKey.keySet())
            && otherSet.removeOperationsByKey.keySet().containsAll(removeOperationsByKey.keySet());
    }

    public void merge(LwwElementSet otherSet) {
        for (ElementOperation otherAddOperation : otherSet.addOperationsByKey.values()) {
            ElementOperation currentAddOperation = addOperationsByKey.get(otherAddOperation.key);
            if (currentAddOperation == null
                || isNewerOrEqual(currentAddOperation, otherAddOperation)) {
                addOperationsByKey.put(otherAddOperation.key, otherAddOperation);
            }
        }

        for (ElementOperation otherRemoveOperation : otherSet.removeOperationsByKey.values()) {
            ElementOperation currentRemoveOperation = removeOperationsByKey.get(otherRemoveOperation.key);
            if (currentRemoveOperation == null
                || isNewerOrEqual(currentRemoveOperation, otherRemoveOperation)) {
                removeOperationsByKey.put(otherRemoveOperation.key, otherRemoveOperation);
            }
        }
    }

    public boolean isNewerOrEqual(ElementOperation currentOperation, ElementOperation candidateOperation) {
        if (currentOperation.bias != candidateOperation.bias) {
            throw new IllegalArgumentException("Invalid bias value");
        }

        Bias operationBias = currentOperation.bias;
        int timestampComparison = Integer.compare(currentOperation.timestamp, candidateOperation.timestamp);

        if (timestampComparison == 0) {
            return operationBias != Bias.ADDS;
        }
        return timestampComparison < 0;
    }
}