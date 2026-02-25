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
 * Breadth-first search implementation over a tree of {@link Node}s.
 */
public class Class1<T> {

    private final List<T> visitedOrder = new ArrayList<>();
    private final Set<T> visitedValues = new HashSet<>();

    /**
     * Performs a breadth-first search starting from the given root node
     * looking for the specified target value.
     *
     * @param root   the starting node of the search
     * @param target the value to search for
     * @return an {@link Optional} containing the found node, or empty if not found
     */
    public Optional<Node<T>> method1(final Node<T> root, final T target) {
        if (root == null) {
            return Optional.empty();
        }

        T rootValue = root.getValue();
        visitedOrder.add(rootValue);
        visitedValues.add(rootValue);

        if (valuesEqual(rootValue, target)) {
            return Optional.of(root);
        }

        Queue<Node<T>> queue = new ArrayDeque<>(root.getChildren());
        while (!queue.isEmpty()) {
            final Node<T> currentNode = queue.poll();
            T currentValue = currentNode.getValue();

            if (visitedValues.contains(currentValue)) {
                continue;
            }

            visitedOrder.add(currentValue);
            visitedValues.add(currentValue);

            if (valuesEqual(currentValue, target)) {
                return Optional.of(currentNode);
            }

            queue.addAll(currentNode.getChildren());
        }

        return Optional.empty();
    }

    /**
     * Returns the values in the order they were visited during the last search.
     *
     * @return a list of visited values in BFS order
     */
    public List<T> method2() {
        return visitedOrder;
    }

    private boolean valuesEqual(T first, T second) {
        if (first == second) {
            return true;
        }
        if (first == null || second == null) {
            return false;
        }
        return first.equals(second);
    }
}