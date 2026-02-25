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
 * Breadth-First Search implementation for tree/graph traversal.
 *
 * @author caos321
 * @co-author @manishraj27
 * @see <a href="https://en.wikipedia.org/wiki/Breadth-first_search">Breadth-first search</a>
 */
public class BreadthFirstSearch<T> {

    private final List<T> visited = new ArrayList<>();
    private final Set<T> visitedSet = new HashSet<>();

    /**
     * Performs a breadth-first search to find a node with the given value.
     *
     * @param root  the root node to start the search from
     * @param value the value to search for
     * @return an {@link Optional} containing the found node, or empty if not found
     */
    public Optional<Node<T>> search(final Node<T> root, final T value) {
        if (root == null) {
            return Optional.empty();
        }

        clearVisited();

        markVisited(root.getValue());

        if (valuesEqual(root.getValue(), value)) {
            return Optional.of(root);
        }

        Queue<Node<T>> queue = new ArrayDeque<>(root.getChildren());

        while (!queue.isEmpty()) {
            Node<T> current = queue.poll();
            T currentValue = current.getValue();

            if (visitedSet.contains(currentValue)) {
                continue;
            }

            markVisited(currentValue);

            if (valuesEqual(currentValue, value)) {
                return Optional.of(current);
            }

            queue.addAll(current.getChildren());
        }

        return Optional.empty();
    }

    /**
     * Returns the list of node values in the order they were visited.
     *
     * @return list containing the visited node values
     */
    public List<T> getVisited() {
        return visited;
    }

    private void clearVisited() {
        visited.clear();
        visitedSet.clear();
    }

    private void markVisited(T value) {
        visited.add(value);
        visitedSet.add(value);
    }

    private boolean valuesEqual(T a, T b) {
        return a == b || (a != null && a.equals(b));
    }
}