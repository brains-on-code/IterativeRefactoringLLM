package com.thealgorithms.searches;

import com.thealgorithms.datastructures.Node;
import java.util.*;

/**
 * Breadth-first search implementation.
 */
public class BreadthFirstSearch<T> {

    private final List<T> visited = new ArrayList<>();
    private final Set<T> visitedSet = new HashSet<>();

    public Optional<Node<T>> search(final Node<T> root, final T target) {
        if (root == null) {
            return Optional.empty();
        }

        final Queue<Node<T>> queue = new ArrayDeque<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            final Node<T> current = queue.poll();
            final T currentValue = current.getValue();

            if (!visitedSet.add(currentValue)) {
                continue;
            }
            visited.add(currentValue);

            if (Objects.equals(currentValue, target)) {
                return Optional.of(current);
            }

            queue.addAll(current.getChildren());
        }

        return Optional.empty();
    }

    public List<T> getVisited() {
        return visited;
    }
}