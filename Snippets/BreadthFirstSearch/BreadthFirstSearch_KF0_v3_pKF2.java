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
 * Breadth-First Search (BFS) implementation for tree/graph traversal.
 *
 * @author caos321
 * @co-author @manishraj27
 * @see <a href="https://en.wikipedia.org/wiki/Breadth-first_search">Breadth-first search</a>
 */
public class BreadthFirstSearch<T> {

    /** Values in the order they were visited during the last search. */
    private final List<T> visited = new ArrayList<>();

    /** Set of visited values for O(1) membership checks during search. */
    private final Set<T> visitedSet = new HashSet<>();

    /**
     * Performs a breadth-first search starting from {@code root} to find a node
     * whose value equals {@code value}.
     *
     * @param root  the starting node of the search; may be {@code null}
     * @param value the value to search for
     * @return an {@link Optional} containing the found node, or {@link Optional#empty()}
     *         if no such node exists
     */
    public Optional<Node<T>> search(final Node<T> root, final T value) {
        if (root == null) {
            return Optional.empty();
        }

        resetVisited();
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

    /** Returns the values visited during the most recent search, in visit order. */
    public List<T> getVisited() {
        return visited;
    }

    /** Clears all visited tracking structures in preparation for a new search. */
    private void resetVisited() {
        visited.clear();
        visitedSet.clear();
    }

    /** Records that a value has been visited. */
    private void markVisited(T value) {
        visited.add(value);
        visitedSet.add(value);
    }

    /** Null-safe equality check for values. */
    private boolean valuesEqual(T a, T b) {
        return a == b || (a != null && a.equals(b));
    }
}