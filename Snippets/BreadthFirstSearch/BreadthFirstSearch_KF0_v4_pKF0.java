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
 * Breadth-First Search implementation for tree/graph traversal.
 *
 * @author caos321
 * @co-author @manishraj27
 * @see <a href="https://en.wikipedia.org/wiki/Breadth-first_search">Breadth-first search</a>
 */
public class BreadthFirstSearch<T> {

    private final List<T> visitedOrder = new ArrayList<>();
    private final Set<T> visitedSet = new HashSet<>();

    /**
     * Performs a breadth-first search to find a node with the given value.
     *
     * @param root  The root node to start the search from
     * @param value The value to search for
     * @return Optional containing the found node, or empty if not found
     */
    public Optional<Node<T>> search(final Node<T> root, final T value) {
        if (root == null) {
            return Optional.empty();
        }

        resetVisitedState();

        final Queue<Node<T>> queue = new ArrayDeque<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            final Node<T> currentNode = queue.poll();
            final T currentValue = currentNode.getValue();

            if (isAlreadyVisited(currentValue)) {
                continue;
            }

            markVisited(currentValue);

            if (Objects.equals(currentValue, value)) {
                return Optional.of(currentNode);
            }

            queue.addAll(currentNode.getChildren());
        }

        return Optional.empty();
    }

    private void resetVisitedState() {
        visitedOrder.clear();
        visitedSet.clear();
    }

    private boolean isAlreadyVisited(final T value) {
        return visitedSet.contains(value);
    }

    private void markVisited(final T value) {
        visitedSet.add(value);
        visitedOrder.add(value);
    }

    /**
     * Returns the list of node values in the order they were visited.
     *
     * @return List containing the visited node values
     */
    public List<T> getVisited() {
        return new ArrayList<>(visitedOrder);
    }
}