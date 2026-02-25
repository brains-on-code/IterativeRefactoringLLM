package com.thealgorithms.datastructures.crdt;

import java.util.HashMap;
import java.util.Map;

class ElementOperation {
    String elementKey;
    int timestamp;
    Bias bias;

    ElementOperation(String elementKey, int timestamp, Bias bias) {
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
    private final Map<String, ElementOperation> addOperationsByElementKey;
    private final Map<String, ElementOperation> removeOperationsByElementKey;

    LwwElementSet() {
        this.addOperationsByElementKey = new HashMap<>();
        this.removeOperationsByElementKey = new HashMap<>();
    }

    public void add(ElementOperation addOperation) {
        addOperationsByElementKey.put(addOperation.elementKey, addOperation);
    }

    public void remove(ElementOperation removeOperation) {
        if (canRemove(removeOperation)) {
            removeOperationsByElementKey.put(removeOperation.elementKey, removeOperation);
        }
    }

    public boolean canRemove(ElementOperation removeOperation) {
        ElementOperation lastAddOperation = addOperationsByElementKey.get(removeOperation.elementKey);
        ElementOperation lastRemoveOperation = removeOperationsByElementKey.get(removeOperation.elementKey);

        return lastAddOperation != null
            && (lastRemoveOperation == null || lastAddOperation.timestamp > lastRemoveOperation.timestamp);
    }

    public boolean isSubsetOf(LwwElementSet otherSet) {
        return otherSet.addOperationsByElementKey.keySet().containsAll(addOperationsByElementKey.keySet())
            && otherSet.removeOperationsByElementKey.keySet().containsAll(removeOperationsByElementKey.keySet());
    }

    public void merge(LwwElementSet otherSet) {
        for (ElementOperation otherAddOperation : otherSet.addOperationsByElementKey.values()) {
            ElementOperation currentAddOperation = addOperationsByElementKey.get(otherAddOperation.elementKey);
            if (currentAddOperation == null
                || isNewerOrEqual(currentAddOperation, otherAddOperation)) {
                addOperationsByElementKey.put(otherAddOperation.elementKey, otherAddOperation);
            }
        }

        for (ElementOperation otherRemoveOperation : otherSet.removeOperationsByElementKey.values()) {
            ElementOperation currentRemoveOperation = removeOperationsByElementKey.get(otherRemoveOperation.elementKey);
            if (currentRemoveOperation == null
                || isNewerOrEqual(currentRemoveOperation, otherRemoveOperation)) {
                removeOperationsByElementKey.put(otherRemoveOperation.elementKey, otherRemoveOperation);
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