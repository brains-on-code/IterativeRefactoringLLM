package com.thealgorithms.searches;

import com.thealgorithms.datastructures.Node;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;

/**
 * Breadth-first search implementation over a tree of {@link Node}s.
 */
public class BreadthFirstSearch<T> {

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
    public Optional<Node<T>> search(final Node<T> root, final T target) {
        if (root == null) {
            return Optional.empty();
        }

        resetVisitedState();

        final Queue<Node<T>> queue = new ArrayDeque<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            final Node<T> currentNode = queue.poll();
            if (currentNode == null) {
                continue;
            }

            final T currentValue = currentNode.getValue();
            if (isAlreadyVisited(currentValue)) {
                continue;
            }

            markVisited(currentValue);

            if (Objects.equals(currentValue, target)) {
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
    public List<T> getVisitedOrder() {
        return new ArrayList<>(visitedOrder);
    }

    private void resetVisitedState() {
        visitedOrder.clear();
        visitedValues.clear();
    }

    private boolean isAlreadyVisited(final T value) {
        return visitedValues.contains(value);
    }

    private void markVisited(final T value) {
        visitedValues.add(value);
        visitedOrder.add(value);
    }
}