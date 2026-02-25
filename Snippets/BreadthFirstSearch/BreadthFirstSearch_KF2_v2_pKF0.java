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
        queue.offer(root);

        while (!queue.isEmpty()) {
            final Node<T> currentNode = queue.poll();
            final T currentValue = currentNode.getValue();

            if (!visitedSet.add(currentValue)) {
                continue;
            }

            visited.add(currentValue);

            if (Objects.equals(currentValue, target)) {
                return Optional.of(currentNode);
            }

            for (Node<T> child : currentNode.getChildren()) {
                if (!visitedSet.contains(child.getValue())) {
                    queue.offer(child);
                }
            }
        }

        return Optional.empty();
    }

    public List<T> getVisited() {
        return Collections.unmodifiableList(visited);
    }
}