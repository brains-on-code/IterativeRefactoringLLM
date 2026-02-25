package com.thealgorithms.datastructures.crdt;

import java.util.HashMap;
import java.util.Map;

class ElementOperation {
    String elementId;
    int timestamp;
    Bias bias;

    ElementOperation(String elementId, int timestamp, Bias bias) {
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
    private final Map<String, ElementOperation> addOperations;
    private final Map<String, ElementOperation> removeOperations;

    LwwElementSet() {
        this.addOperations = new HashMap<>();
        this.removeOperations = new HashMap<>();
    }

    public void add(ElementOperation operation) {
        addOperations.put(operation.elementId, operation);
    }

    public void remove(ElementOperation operation) {
        if (canRemove(operation)) {
            removeOperations.put(operation.elementId, operation);
        }
    }

    public boolean canRemove(ElementOperation operation) {
        ElementOperation addOperation = addOperations.get(operation.elementId);
        ElementOperation removeOperation = removeOperations.get(operation.elementId);

        return addOperation != null
            && (removeOperation == null || addOperation.timestamp > removeOperation.timestamp);
    }

    public boolean isSubsetOf(LwwElementSet otherSet) {
        return otherSet.addOperations.keySet().containsAll(addOperations.keySet())
            && otherSet.removeOperations.keySet().containsAll(removeOperations.keySet());
    }

    public void merge(LwwElementSet otherSet) {
        for (ElementOperation otherAddOperation : otherSet.addOperations.values()) {
            ElementOperation existingAddOperation = addOperations.get(otherAddOperation.elementId);
            if (existingAddOperation == null
                || isNewerOrEqual(existingAddOperation, otherAddOperation)) {
                addOperations.put(otherAddOperation.elementId, otherAddOperation);
            }
        }

        for (ElementOperation otherRemoveOperation : otherSet.removeOperations.values()) {
            ElementOperation existingRemoveOperation = removeOperations.get(otherRemoveOperation.elementId);
            if (existingRemoveOperation == null
                || isNewerOrEqual(existingRemoveOperation, otherRemoveOperation)) {
                removeOperations.put(otherRemoveOperation.elementId, otherRemoveOperation);
            }
        }
    }

    public boolean isNewerOrEqual(ElementOperation existingOperation, ElementOperation candidateOperation) {
        if (existingOperation.bias != candidateOperation.bias) {
            throw new IllegalArgumentException("Invalid bias value");
        }

        Bias operationBias = existingOperation.bias;
        int timestampComparison = Integer.compare(existingOperation.timestamp, candidateOperation.timestamp);

        if (timestampComparison == 0) {
            return operationBias != Bias.ADDS;
        }
        return timestampComparison < 0;
    }
}