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
    private final Map<String, ElementOperation> addOperationByElement;
    private final Map<String, ElementOperation> removeOperationByElement;

    LwwElementSet() {
        this.addOperationByElement = new HashMap<>();
        this.removeOperationByElement = new HashMap<>();
    }

    public void add(ElementOperation addOperation) {
        addOperationByElement.put(addOperation.elementKey, addOperation);
    }

    public void remove(ElementOperation removeOperation) {
        if (canRemove(removeOperation)) {
            removeOperationByElement.put(removeOperation.elementKey, removeOperation);
        }
    }

    public boolean canRemove(ElementOperation removeOperation) {
        ElementOperation lastAddOperation = addOperationByElement.get(removeOperation.elementKey);
        ElementOperation lastRemoveOperation = removeOperationByElement.get(removeOperation.elementKey);

        return lastAddOperation != null
            && (lastRemoveOperation == null || lastAddOperation.timestamp > lastRemoveOperation.timestamp);
    }

    public boolean isSubsetOf(LwwElementSet otherSet) {
        return otherSet.addOperationByElement.keySet().containsAll(addOperationByElement.keySet())
            && otherSet.removeOperationByElement.keySet().containsAll(removeOperationByElement.keySet());
    }

    public void merge(LwwElementSet otherSet) {
        for (ElementOperation otherAddOperation : otherSet.addOperationByElement.values()) {
            ElementOperation currentAddOperation = addOperationByElement.get(otherAddOperation.elementKey);
            if (currentAddOperation == null
                || isNewerOrEqual(currentAddOperation, otherAddOperation)) {
                addOperationByElement.put(otherAddOperation.elementKey, otherAddOperation);
            }
        }

        for (ElementOperation otherRemoveOperation : otherSet.removeOperationByElement.values()) {
            ElementOperation currentRemoveOperation = removeOperationByElement.get(otherRemoveOperation.elementKey);
            if (currentRemoveOperation == null
                || isNewerOrEqual(currentRemoveOperation, otherRemoveOperation)) {
                removeOperationByElement.put(otherRemoveOperation.elementKey, otherRemoveOperation);
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