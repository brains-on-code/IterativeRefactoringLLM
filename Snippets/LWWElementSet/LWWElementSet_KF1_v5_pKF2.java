package com.thealgorithms.datastructures.crdt;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a single add or remove operation on an element in the OR-Set.
 */
class Operation {
    final String element;
    final int version;
    final Bias bias;

    Operation(String element, int version, Bias bias) {
        this.element = element;
        this.version = version;
        this.bias = bias;
    }
}

/**
 * Bias used to resolve conflicts between concurrent operations.
 */
enum Bias {
    /** Prefer additions when resolving concurrent operations. */
    ADDS,

    /** Prefer removals when resolving concurrent operations. */
    REMOVALS
}

/**
 * A bias-based Observed-Remove Set (OR-Set) CRDT.
 *
 * <p>For each element, tracks the latest add and remove operation and supports:
 * <ul>
 *   <li>validation of removals,</li>
 *   <li>subset checks between replicas, and</li>
 *   <li>merging state from another replica.</li>
 * </ul>
 */
class BiasedORSet {
    private final Map<String, Operation> adds = new HashMap<>();
    private final Map<String, Operation> removals = new HashMap<>();

    /**
     * Records an add operation for the given element.
     */
    public void add(Operation op) {
        adds.put(op.element, op);
    }

    /**
     * Records a remove operation if it is causally valid.
     */
    public void remove(Operation op) {
        if (isRemovalValid(op)) {
            removals.put(op.element, op);
        }
    }

    /**
     * A removal is valid if:
     * <ul>
     *   <li>there exists a prior add for the same element, and</li>
     *   <li>there is no removal with a version greater than or equal to that add.</li>
     * </ul>
     */
    public boolean isRemovalValid(Operation op) {
        Operation addOp = adds.get(op.element);
        Operation removeOp = removals.get(op.element);

        return addOp != null && (removeOp == null || addOp.version > removeOp.version);
    }

    /**
     * Returns {@code true} if all adds and removals in this set are also present
     * in {@code other}.
     */
    public boolean isSubsetOf(BiasedORSet other) {
        return other.adds.keySet().containsAll(adds.keySet())
            && other.removals.keySet().containsAll(removals.keySet());
    }

    /**
     * Merges another {@link BiasedORSet} into this one.
     *
     * <p>For each element, keeps the dominating operation (by version and bias)
     * among the two replicas.</p>
     */
    public void merge(BiasedORSet other) {
        mergeOperations(other.adds, adds);
        mergeOperations(other.removals, removals);
    }

    private void mergeOperations(Map<String, Operation> source, Map<String, Operation> target) {
        for (Operation incoming : source.values()) {
            Operation current = target.get(incoming.element);
            if (current == null || shouldReplace(current, incoming)) {
                target.put(incoming.element, incoming);
            }
        }
    }

    /**
     * Returns {@code true} if {@code candidate} should replace {@code current}
     * based on version and bias.
     *
     * <p>Rules:
     * <ul>
     *   <li>Higher version always dominates.</li>
     *   <li>If versions are equal, the configured bias decides.</li>
     * </ul>
     *
     * @throws IllegalArgumentException if the operations have different biases
     */
    public boolean shouldReplace(Operation current, Operation candidate) {
        if (current.bias != candidate.bias) {
            throw new IllegalArgumentException("Operations must have the same bias");
        }

        int comparison = Integer.compare(current.version, candidate.version);

        if (comparison == 0) {
            return current.bias == Bias.REMOVALS;
        }

        return comparison < 0;
    }
}