package com.thealgorithms.searches;

import com.thealgorithms.datastructures.Node;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;

/**
 * Breadth-first search (BFS) over a tree of {@link Node} objects.
 *
 * @param <T> the type of values stored in the nodes
 */
public class BreadthFirstSearch<T> {

    /** Nodes visited during the last BFS execution, in visitation order. */
    private final List<T> visitedOrder = new ArrayList<>();

    /** Set of visited node values to avoid processing the same value twice. */
    private final Set<T> visitedValues = new HashSet<>();

    /**
     * Performs a breadth-first search starting from the given root node,
     * looking for the first node whose value equals the target value.
     *
     * @param root   the starting node of the search; may be {@code null}
     * @param target the value to search for; may be {@code null}
     * @return an {@link Optional} containing the found node, or empty if not found
     */
    public Optional<Node<T>> search(final Node<T> root, final T target) {
        if (root == null) {
            return Optional.empty();
        }

        visitedOrder.clear();
        visitedValues.clear();

        visitedOrder.add(root.getValue());
        visitedValues.add(root.getValue());

        if (valuesEqual(root.getValue(), target)) {
            return Optional.of(root);
        }

        Queue<Node<T>> queue = new ArrayDeque<>(root.getChildren());
        while (!queue.isEmpty()) {
            final Node<T> current = queue.poll();
            T currentValue = current.getValue();

            if (visitedValues.contains(currentValue)) {
                continue;
            }

            visitedOrder.add(currentValue);
            visitedValues.add(currentValue);

            if (valuesEqual(currentValue, target)) {
                return Optional.of(current);
            }

            queue.addAll(current.getChildren());
        }

        return Optional.empty();
    }

    /**
     * Returns the values of nodes visited during the last BFS execution,
     * in the order they were visited.
     *
     * @return a list of visited node values
     */
    public List<T> getVisitedOrder() {
        return visitedOrder;
    }

    private boolean valuesEqual(T a, T b) {
        return a == b || (a != null && a.equals(b));
    }
}