package com.thealgorithms.datastructures.crdt;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple implementation of an Observed-Remove Set (OR-Set) CRDT.
 *
 * <p>This CRDT tracks additions and removals with associated timestamps (or
 * versions) and supports merging and comparison between replicas.</p>
 */
class Operation {
    String element;
    int version;
    Bias bias;

    /**
     * Creates a new operation on an element.
     *
     * @param element the element being added or removed
     * @param version the version or timestamp of the operation
     * @param bias    whether this operation is an ADD or a REMOVAL
     */
    Operation(String element, int version, Bias bias) {
        this.element = element;
        this.version = version;
        this.bias = bias;
    }
}

enum Bias {
    /**
     * Bias towards additions when resolving concurrent operations.
     */
    ADDS,

    /**
     * Bias towards removals when resolving concurrent operations.
     */
    REMOVALS
}

/**
 * A bias-based OR-Set CRDT that tracks add and remove operations per element.
 */
class BiasedORSet {
    private final Map<String, Operation> adds;
    private final Map<String, Operation> removals;

    /**
     * Creates an empty BiasedORSet.
     */
    BiasedORSet() {
        this.adds = new HashMap<>();
        this.removals = new HashMap<>();
    }

    /**
     * Records an add operation.
     *
     * @param op the add operation to record
     */
    public void add(Operation op) {
        adds.put(op.element, op);
    }

    /**
     * Records a remove operation if it is causally valid.
     *
     * @param op the remove operation to record
     */
    public void remove(Operation op) {
        if (isRemovalValid(op)) {
            removals.put(op.element, op);
        }
    }

    /**
     * Checks whether a removal operation is causally valid, i.e., whether there
     * exists a corresponding add with a strictly smaller version and no
     * dominating removal.
     *
     * @param op the removal operation to validate
     * @return true if the removal is valid, false otherwise
     */
    public boolean isRemovalValid(Operation op) {
        Operation addOp = adds.get(op.element);
        Operation removeOp = removals.get(op.element);

        return addOp != null && (removeOp == null || addOp.version > removeOp.version);
    }

    /**
     * Checks whether this set is a subset of another set, considering both
     * additions and removals.
     *
     * @param other the other BiasedORSet to compare with
     * @return true if this set is a subset of {@code other}, false otherwise
     */
    public boolean isSubsetOf(BiasedORSet other) {
        return other.adds.keySet().containsAll(adds.keySet())
            && other.removals.keySet().containsAll(removals.keySet());
    }

    /**
     * Merges another BiasedORSet into this one, taking the dominant operation
     * per element according to version and bias.
     *
     * @param other the other BiasedORSet to merge from
     */
    public void merge(BiasedORSet other) {
        for (Operation op : other.adds.values()) {
            if (!adds.containsKey(op.element) || dominates(adds.get(op.element), op)) {
                adds.put(op.element, op);
            }
        }

        for (Operation op : other.removals.values()) {
            if (!removals.containsKey(op.element) || dominates(removals.get(op.element), op)) {
                removals.put(op.element, op);
            }
        }
    }

    /**
     * Determines whether {@code candidate} should replace {@code current}
     * according to version and bias.
     *
     * @param current   the current operation stored
     * @param candidate the new operation to compare
     * @return true if {@code candidate} dominates {@code current}, false otherwise
     * @throws IllegalArgumentException if the operations have different biases
     */
    public boolean dominates(Operation current, Operation candidate) {
        if (current.bias != candidate.bias) {
            throw new IllegalArgumentException("Invalid bias value");
        }

        Bias bias = current.bias;
        int comparison = Integer.compare(current.version, candidate.version);

        if (comparison == 0) {
            // Same version: resolve by bias
            return bias != Bias.ADDS;
        }

        // Higher version dominates
        return comparison < 0;
    }
}